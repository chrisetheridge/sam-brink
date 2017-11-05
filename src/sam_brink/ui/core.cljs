(ns sam-brink.ui.core
  (:require
   [rum.core :as rum]
   [clojure.string :as str]
   [sam-brink.util :as util]
   [sam-brink.routing :as routing]))

(rum/defcs navigation < (rum/local false ::active?)
  [{*active? ::active?} db current-route]
  (let [change-page-fn (fn [url]
                         #(routing/go! url))]
    [:.navbar
     [:.navbar-brand
      [:a.navbar-item
       {:on-click (change-page-fn ::home)}
       [:img {:class ["image" ""]
              :src   (util/static-image "hand.png")}]]
      [:button.button.navbar-burger
       {:on-click     #(swap! *active? not)
        :on-touch-end #(swap! *active? not)}
       [:span]
       [:span]
       [:span]]]
     [:.navbar-menu
      {:class (when @*active? ["is-active"])}
      [:.navbar-start]
      [:.navbar-end
       (for [{:route/keys [label url key]} (:routes db)
             :let [active? (= (:route/key current-route) key)
                   kn      (name key)]]
         [:a.navbar-item {:key (str "nav/" kn)
                          :on-click (change-page-fn (str "/#/" kn))}
          label])]]]))

(rum/defc container-wrapper [db component]
  (let [current-route (routing/current-route)]
    [:div.root
     (navigation db current-route)
     [:.container
      [:.content
       [:h1.title (:route/label current-route)]
       [:section.section.container-page
        (component db)]]]]))
