(ns sam-brink.components
  (:require [rum.core :as rum]
            [clojure.string :as str]
            [sam-brink.util :as util]))

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

(rum/defcs navigation < (rum/local false ::active?)
  [{*active? ::active?} db *page]
  (let [change-page-fn (fn [page]
                         #(reset! *page page))
        page           @*page]
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
       (for [{:keys [title key]} (:pages db)
             :let                [active? (= page key)]]
         [:a.navbar-item
          {:on-click (change-page-fn key)}
          title])]]]))

(rum/defc footer [db])

(rum/defc home-post
  [db]
  (let [{:project/keys [title image
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

(rum/defc project-card
  [*open-project {:project/keys [id image description title] :as project}]
  [:.project-card {:key id}
   [:.column
    [:.card
     [:.card-image
      [:figure {:class ["image" "is-1by1"]}
       [:img {:src (util/static-project-image image)}]]]
     [:.card-content
      [:h1.title title]
      [:p
       [:a.button.more-info-button
        {:on-click #(reset! *open-project id)}
        "View more"]]]]]
   #_(when (= id @*open-project)
     [:.modal.is-active
      [:.modal-background
       {:on-click #(reset! *open-project nil)}]
      [:.modal-card
       [:header.modal-card-head
        [:p.modal-card-title title]
        [:button.delete {:on-click #(reset! *open-project nil)}]]
       [:section.modal-card-body
        [:.content.is-text
         [:figure {:class ["image" "is-4x2"]}
          [:img {:src (util/static-project-image image)}]]
         [:p description]]]
       [:footer.modal-card-foot]]])])

(rum/defc full-project [*open-project {:project/keys [id image description title] :as project}]
  [:.full-project-container {:key id}
   [:.content.is-text
    [:h1.project-header
     [:a.button.is-small.no-border
      {:on-click #(reset! *open-project nil)}
      "← Back"]
     [:span.title title]]
    [:figure.figure.image.is-16by9
     [:img {:src (util/static-project-image image)}]]
    [:p {:dangerouslySetInnerHTML {:__html description}}]]])

(rum/defcs project-listing < (rum/local nil ::open-project)
  (rum/local nil ::index)
  [{*open-project ::open-project
    *index        ::index}
   {projects :projects :as db}]
  [:.container
   (prn @*open-project)
   (let [projects-by-id (->> (map (juxt :project/id identity) projects)
                             (into {}))]
     (if-some [project (get projects-by-id @*open-project)]
       (full-project *open-project project)
       (let [count (count projects)]
         (for [row (partition-all 3 projects)]
           [:.columns
            (map #(rum/with-key
                    (project-card *open-project %)
                    (:project/id %)) row)]))))])
