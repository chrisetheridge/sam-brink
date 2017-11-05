(ns sam-brink.ui.pages.my-story
  (:require
   [rum.core :as rum]
   [sam-brink.util :as util]))

(rum/defc my-story [db]
  [:.container
   [:h2 "My story"]
   [:.content.is-text
    [:p (util/random-str 12)]
    [:figure {:class ["image" "is-4by2"]}
     [:img {:src (util/static-image "triple_print_mock.jpg")}]]]
   [:.content.is-text
    [:h3 "How it started"]
    [:p (util/random-str 4)]
    [:figure {:class ["image" "is-2by1"]}
     [:img {:src (util/static-image "outdoor.jpg")}]]]
   [:.content.is-text
    [:h3 "What I am doing now"]
    [:p (util/random-str 8)]]
   [:.content.is-text
    [:h3 "My CV"]
    [:p (util/random-str 5)]
    [:.download-cv
     [:button.button.is-info "Download PDF"]]]])

(rum/defc page [db]
  (my-story db))
