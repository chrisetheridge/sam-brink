(ns sam-brink.routing
  (:require
   [rum.core :as rum]

   [sam-brink.state :as state]))

(defonce ^:private *current-route (atom nil))

(defonce ^:private *routes (atom {}))

(defn location []
  js/document.location)

(defn location-hash
  ([] (location-hash (location)))
  ([l]
   (.-hash l)))

(defonce *hash (atom (location-hash)))

(defn add-route!
  [{key :route/key :as r}]
  (swap! *routes assoc key r))

(defn route-matches? [{:route/keys [match]} loc]
  (cond
    (regexp? match) (re-matches match (location-hash loc))
    :else           (= match (location-hash loc))))

(defn- current-route []
  (let [l (location)]
    (first (filter (fn [[k r]]
                     (route-matches? r l))
                   @*routes))))

(defn render-route []
  (if-let [[_ {:route/keys [key component] :as r}] (current-route)]
    (do
      (reset! *current-route key)
      (let [l (location)
            h (location-hash l)]
        (prn "[routing] starting with location hash " h)
        (reset! *hash h)
        (prn (str "[routing] rendering route " key))
        (rum/mount (component (state/current-state)) (js/document.getElementById "app"))))
    (throw (ex-info (str "No route found for " (location-hash (location)))
                    {:location (location)}))))

(defn go! [url]
  (js/history.pushState nil "" url)
  (render-route))

(defn refresh []
  (set! js/onpopstate render-route)
  (render-route))

(defn start-routes! [routes *db]
  (swap! *db assoc :routes routes)
  (doseq [{:route/keys [match] :as r} routes]
    (prn (str "[routes] adding route " match))
    (add-route! r)))
