(ns sam-brink.ui.pages.projects
  (:require
   [rum.core :as rum]
   [sam-brink.util :as util]))

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
        "View more"]]]]]])

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
   db]
  [:.container
   [:.content.is-text
    [:h1.title "My projects"]]
   (let [projects (:projects db)
         projects-by-id (->> (map (juxt :project/id identity) projects)
                             (into {}))]
     (if-some [project (get projects-by-id @*open-project)]
       (full-project *open-project project)
       (let [count (count projects)]
         (for [row (partition-all 3 projects)]
           [:.columns {:key (str "column/" (hash row))}
            (map #(rum/with-key
                    (project-card *open-project %)
                    (:project/id %)) row)]))))])

(rum/defc project-listing-page [db]
  (project-listing db))
