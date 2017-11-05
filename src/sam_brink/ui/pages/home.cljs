(ns sam-brink.ui.pages.home
  (:require
   [rum.core :as rum]
   [sam-brink.util :as util]))

(rum/defc featured-post [db]
  (let [{:project/keys [title image description excerpt]} (:featured-project db)]
    [:section.hero
     [:.hero.hero-body
      [:.container
       [:.hero-image
        [:figure {:class ["image" "is-4by2"]}
         [:img {:src (util/static-project-image image)}]]
        [:.hero-image-caption
         [:p.small excerpt]]]
       [:h2.title.hero-title title]
       [:h5.subtitle "Featured project"]
       [:.subtitle.hero-description
        [:p description]]]]]))

(rum/defc page [db]
  (featured-post db))
