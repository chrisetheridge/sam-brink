(ns sam-brink.core
  (:require [rum.core :as rum]
            [clojure.string :as str]
            [sam-brink.components :as components]
            [sam-brink.util :as util]))

(enable-console-print!)

(def pages
  [{:title     "Home"
    :component components/home-post
    :key       ::home}
   {:title     "Projects"
    :component components/project-listing
    :key       ::projects}
   {:title     "My story"
    :component components/my-story
    :key       ::my-story}
   {:title     "Contact"
    :component components/contact-me
    :key       ::contact-me}])

(def state
  {:projects         [{:id          1
                       :title       "Hand"
                       :image       "hand.png"
                       :description (util/random-str 5)}
                      {:id          2
                       :title       "Leather book"
                       :image       "leather_book.jpg"
                       :description (util/random-str 5)}
                      {:id          3
                       :title       "Outdoor"
                       :image       "outdoor.jpg"
                       :description (util/random-str 5)}
                      {:id          4
                       :title       "Personal book"
                       :image       "book.jpg"
                       :description (util/random-str 5)}]
   :featured-project {:id          2
                      :title       "Leather book"
                      :image       "leather_book.jpg"
                      :description (util/random-str 5)}
   :pages pages})

(rum/defcs app < (rum/local ::projects ::current-page)
  [{*page ::current-page} db]
  (let [page @*page]
    [:div.root
     (components/navigation db *page)
     [:.container
      [:.content
       [:h1.title (str/capitalize (name page))]
       [:section.section.container-page
        (for [{:keys [title component key]} (:pages db)
              :let [active? (= page key)]]
          [:.container-page-inner
           {:class (when active? ["active"])}
           (when active?
             (component db))])]]]
     #_[:footer.footer
      (components/footer db)]]))

(rum/mount (app state)
           (.getElementById js/document "app"))
