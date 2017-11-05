(ns sam-brink.ui.routes
  (:require
   [sam-brink.ui.core :as ui.core]
   [sam-brink.ui.pages.contact-me :as pages.contact-me]
   [sam-brink.ui.pages.home :as pages.home]
   [sam-brink.ui.pages.my-story :as pages.my-story]
   [sam-brink.ui.pages.projects :as pages.projects]))

(def routes
  (->> [{:route/label     "Home"
         :route/match  #"/|#/home"
         :route/component pages.home/page
         :route/key       ::home}
        {:route/label     "Projects"
         :route/component pages.projects/project-listing-page
         :route/match     "#/projects"
         :route/key       ::projects}
        {:route/label     "My story"
         :route/match     "#/my-story"
         :route/component pages.my-story/page
         :route/key       ::my-story}
        {:route/label     "Contact"
         :route/match     "#/contact-me"
         :route/component pages.contact-me/page
         :route/key       ::contact-me}]
       ;; wrap each component with the container wrapper
       (map (fn [route]
              (update route :route/component
                       (fn [c]
                         #(ui.core/container-wrapper % c)))))))
