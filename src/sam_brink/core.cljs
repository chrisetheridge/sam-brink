(ns sam-brink.core
  (:require [rum.core :as rum]))

(enable-console-print!)

(def projects
  [{:id          1
    :title       "Hand"
    :image       "hand.png"
    :description "My logo"}
   {:id          2
    :title       "Leather book"
    :image       "leather_book.jpg"
    :description "Hand made leather book, that I am making myself."}
   {:id          3
    :title       "Outdoor"
    :image       "outdoor.jpg"
    :description "Picture of a bike next to a peach house, in Zanzibar."}
   {:id          4
    :title       "Personal book"
    :image       "book.jpg"
    :description "This is a book of my lino pictures."}])

(def state
  {:projects         projects
   :featured-project (nth projects 2)})

(defn static-image [path]
  (str "/img/" path))

(rum/defc project-listing [db]
  [:h1.subtitle "Projects"]
  [:.columns
   (for [{:keys [id image description title]} (:projects db)]
     [:.column {:key id}
      [:.card
       [:.card-image
        [:figure {:class ["image" "is-4x2"]}
         [:img {:src (static-image image)}]]]
       [:.card-content
        [:h1.title title]
        [:.content description]
        [:a.button.is-warning.more-info-button "Info"]]]])])

(rum/defc featured-project [db]
  (let [{:keys [title image
                description image-caption]} (:featured-project db)]
    [:section.hero
     [:.hero.hero-body
      [:.container
       [:h1.title.hero-title title]
       [:.hero-image
        [:figure {:class ["image" "is-4by2"]}
         [:img {:src (static-image image)}]]
        [:.hero-image-caption
         [:p.small image-caption]]]
       [:.subtitle.hero-description
        [:p description]]]]]))

(rum/defc navigation [db]
  [:.navbar
   [:.navbar-brand
    [:a.navbar-item
     [:img {:class ["image" "is-4x2"]
            :src   (static-image "hand.png")}]]
    [:.navbar-item
     [:.navbar-link "Projects"]]
    [:.navbar-item
     [:.navbar-link "Featured"]]
    [:.navbar-item
     [:.navbar-link "CV"]]
    [:.navbar-item
     [:.navbar-link "Contact"]]]])

(defn random-str [n]
  (->> (take n (cycle ["Lorem ipsum doelm mit ex zit muipet"]))
       (apply str)))

(rum/defc about-me [db]
  [:.container
   [:h2 "My story"]
   [:p (random-str 12)]
   [:figure {:class ["image" "is-4by2"]}
    [:img {:src (static-image "triple_print_mock.jpg")}]]
   [:h3 "How it started"]
   [:p (random-str 4)]
   [:figure {:class ["image" "is-2by1"]}
    [:img {:src (static-image "outdoor.jpg")}]]
   [:h3 "What I am doing now"]
   [:p (random-str 8)]])

(rum/defc contact-me [db]
  [:.container
   [:h2 "Contact me"]
   [:.contact-me-form
    [:.field
     [:label.label "Name"]
     [:p.control.has-icons-left
      [:input.input {:type        "text"
                     :placeholder "Jon Doe"}]
      [:span.icon.is-small.is-left
       [:i.fa.fa-envelope]]]]
    [:.field
     [:label.label "Email"]
     [:p.control.has-icons-left
      [:input.input {:type        "email"
                     :placeholder "jon@doe.com"}]
      [:span.icon.is-small.is-left
       [:i.fa.fa-envelope]]]]
    [:.field
     [:label.label "Message"]
     [:.control
      [:textarea.textarea
       {:type        "textarea"
        :placeholder "Hello Sam ..."}]]]
    [:.field.is-grouped
     [:.control
      [:button.button.is-info "Submit"]]
     [:.control
      [:button.button.is-text "Cancel"]]]]])

(rum/defc app [db]
  [:div
   (navigation db)
   [:.container
    [:.content
     [:h1.title "Sam Brink"]
     [:section.section
      (featured-project db)]
     [:section.section
      [:.projects-listing
       (project-listing db)]]
     [:section.section
      [:.about-me
       (about-me db)]]
     [:section.section
      [:.contact-me
       (contact-me db)]]]]])

(rum/mount (app state)
           (.getElementById js/document "app"))
