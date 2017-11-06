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
        [:figure {:class ["image" "is-16by9"]}
         [:img {:src (util/static-project-image image)}]]
        [:.hero-image-caption
         [:p.small excerpt]]]
       [:h2.title.hero-title title]
       [:.subtitle.hero-description
        [:p {:dangerouslySetInnerHTML {:__html description}}]]]]]))

(rum/defc page [db]
  [:container
   [:.columns
    [:.column.is-one-third
     [:figure.figure
      [:img {:src (util/static-image "pages/home_1.jpg")}]]]
    [:.column
     [:.content.is-text.is-two-thirds
      [:p "My name is Sam Brink and I am a young creative from Cape Town, South African. This portfolio consists of my most recent and proudest curated visual communication work. "]
      [:.columns
       (for [i (range 1 4)]
         [:.column {:key (str "home-image-vector/" i)}
          [:figure.figure.image
           [:img {:src (util/static-image (str "vector_" i ".png"))}]]])]
      [:p "My strengths are illustration and branding, but I have a particular knack for creating things with my hands. I draw a lot of my inspiration from the  irregular beauty and patterns in nature, bold colours and the more simple things in life. I believe that hand made things are the most true and personalised expression of one’s creative ability."]]]]
   [:.featured-post-container
    [:h2 "Featured project"]
    (featured-post db)]])
