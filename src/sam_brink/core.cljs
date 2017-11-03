(ns sam-brink.core
  (:require [rum.core :as rum]
            [clojure.string :as str]
            [sam-brink.util :as util]))

(enable-console-print!)

(def projects
  [{:id          1
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
    :description (util/random-str 5)}])

(def state
  {:projects         projects
   :featured-project (nth projects 2)})

(rum/defcs project-listing < (rum/local nil ::open-project)
  [{*open-project ::open-project} db]
  [:.container
   [:.content.has-text
    (util/random-str 20)]
   [:.columns
    (for [{:keys [id image description title]} (:projects db)
          :let [close-fn #(reset! *open-project nil)]]
      (list
       [:.column {:key id}
        [:.card
         [:.card-image
          [:figure {:class ["image" "is-4x2"]}
           [:img {:src (util/static-image image)}]]]
         [:.card-content
          [:h1.title title]
          [:.content description]
          [:a.button.is-warning.more-info-button
           {:on-click #(reset! *open-project id)}
           "Info"]]]]
       (when (= id @*open-project)
           [:.modal.is-active
            [:.modal-background
             {:on-click close-fn}]
            [:.modal-card
             [:header.modal-card-head
              [:p.modal-card-title title]
              [:button.delete {:on-click close-fn}]]
             [:section.modal-card-body
              [:.content.is-text
               [:figure {:class ["image" "is-4x2"]}
                [:img {:src (util/static-image image)}]]
               [:p description]]]
             [:footer.modal-card-foot]]])))]])

(rum/defc featured-project
  [db]
  (let [{:keys [title image
                description image-caption]} (:featured-project db)]
    [:section.hero
     [:.hero.hero-body
      [:.container
       [:.hero-image
        [:figure {:class ["image" "is-4by2"]}
         [:img {:src (util/static-image image)}]]
        [:.hero-image-caption
         [:p.small image-caption]]]
       [:h2.title.hero-title title]
       [:h5.subtitle "Featured project"]
       [:.subtitle.hero-description
        [:p description]]]]]))

(rum/defc navigation [db *page]
  [:.navbar
   (let [change-page-fn (fn [page]
                          #(reset! *page page))]
     [:.navbar-brand
      [:a.navbar-item
       {:on-click (change-page-fn ::home)}
       [:img {:class ["image" "is-4x2"]
              :src   (util/static-image "hand.png")}]]
      [:a.navbar-item
       {:on-click (change-page-fn ::projects)}
       "Projects"]
      [:a.navbar-item
       {:on-click (change-page-fn ::about-me)}
       "About me"]
      [:a.navbar-item.is-active
       {:on-click (change-page-fn ::contact-me)}
       "Contact"]])])

(rum/defc about-me [db]
  [:.container
   [:h2 "My story"]
   [:p (util/random-str 12)]
   [:figure {:class ["image" "is-4by2"]}
    [:img {:src (util/static-image "triple_print_mock.jpg")}]]
   [:h3 "How it started"]
   [:p (util/random-str 4)]
   [:figure {:class ["image" "is-2by1"]}
    [:img {:src (util/static-image "outdoor.jpg")}]]
   [:h3 "What I am doing now"]
   [:p (util/random-str 8)]])

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

(def pages
  {::home       featured-project
   ::projects   project-listing
   ::about-me   about-me
   ::contact-me contact-me})

(rum/defcs app < (rum/local ::home ::current-page)
  [{*page ::current-page} db]
  (let [page @*page]
    [:div.root
     (navigation db *page)
     [:.container
      [:.content
       [:h1.title (str/capitalize (name page))]
       [:section.section.container-page
        (for [[k page-comp] pages]
          [:.container-page-inner
           {:class (when (= page k)
                     ["active"])}
           (when (= page k)
             (page-comp db))])]
       (when-not (= page ::contact-me)
         [:section.section
          [:.contact-me
           (contact-me db)]])]]]))

(rum/mount (app state)
           (.getElementById js/document "app"))
