(ns sam-brink.routing
  (:require
   [rum.core :as rum]
   [sam-brink.util :as util]
   [sam-brink.state :as state]))

(defonce ^:private *current-route (atom nil))

(defonce ^:private *routes (atom {}))

(defn location []
  js/document.location)

(defn path
  ([] (path (location)))
  ([l]
   (.-pathname l)))

(defonce *path (atom (path)))

(defn add-route!
  [{key :route/key :as r}]
  (swap! *routes assoc key r))

(defn route-matches? [{:route/keys [match]} loc]
  (= match (path loc)))

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
            p (path l)]
        (reset! *path p)
        (prn (str "[routing] rendering route"  key))
        (prn :state (:routes (state/current-state)))
        (rum/mount (component (state/current-state)) (js/document.getElementById "app"))))
    (throw (ex-info "No route found for " (.-href (location))
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
    (prn (str "[routes] adding route" match))
    (add-route! r)))
