(ns sam-brink.ui.pages.my-story
  (:require
   [rum.core :as rum]
   [sam-brink.util :as util]))

(rum/defc my-story [db]
  [:.container
   [:h2 "My story"]
   [:.content.is-text
    [:p "A little insight into who I am includes knowing my preference for, and strong ability to work with, traditional mediums. I draw a lot of inspiration from nature, organic materials and the textures of raw products (paper, leather, wood, etc.). I also prefer hand crafted art (whether it is commercial or fine art) and perceive it to have more merit than digitalised work, and to be the most honest representation of the artist’s ability and style. I am very dexterous and enjoy using my hands to craft things, as this stemmed from years growing up being exposed to a variety of craftsmanship in my father’s workshop."]
    [:figure {:class ["image" "is-16by9"]}
     [:img {:src (util/static-image "pages/about_me_1.jpg")}]]
    [:h4 "Message"]
    [:p "In this digitally saturated world, filled with computer aided tools, do not discredit the value of hand produced visual languages, unaided by technology. It is okay to use your hands."]
    [:h4 "Essence"]
    [:p "Traditional. Appreciation. Authentic over synthetic."]
    [:h4 "Tone"]
    [:p "Humble, yet proud. Uncomplicated. Honest."]
    [:h4 "Vision"]
    [:p "To be renowned as a confident and experimental illustrator and designer, who enjoys traditional techniques and authentic mediums."]
    [:h4 "Values"]
    [:ul
     [:li "Self-reliance"]
     [:li "Versatility"]
     [:li "Appreciation for traditional ways"]]]
   [:.content.is-text.download-cv-pdf
    [:a.button.is-info
     {:src (util/static-download "Sam Brink CV 2017.pdf")}
     "Download CV"]]])

(rum/defc page [db]
  (my-story db))
