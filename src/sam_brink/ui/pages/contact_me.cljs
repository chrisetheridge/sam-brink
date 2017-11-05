(ns sam-brink.ui.pages.contact-me
  (:require
   [rum.core :as rum]))

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

(rum/defc page [db]
  (contact-me db))
