(ns accrem.views.individual
  (:use hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        accrem.models.client
        accrem.views.common
        accrem.views.client
        accrem.views.sections))

(defn individual-fields [individual]
  [:div {:class "row"}
   [:div {:class "span6"}
    (account-details-section :individual individual)
    (personal-details-section individual)
    (self-assessment-section individual)
    ]

   [:div {:class "span6"}
    (client-contact-section individual)
    (bank-account-section individual)
    (service-plan-section individual)
    ]]
  )

(defn individual-details-page [title formSubmitButton formLink request]
  (page-layout title :individuals {}
    (form-to {:class "form-horizontal"} [:post formLink]
      (individual-fields request)
      [:div {:class "well"}
       (submit-button {:class "btn btn-primary right"} formSubmitButton)
       ])))

(defn render-add-individual [client]
  (individual-details-page "Add" "Add individual" (url-add-individual) client))

(defn render-edit-individual [client]
  (individual-details-page "Edit" "Save individual" (url-edit-individual (:_id client)) client))


(defn render-list-individuals [pageId]
  (let [page (. Integer valueOf pageId)
        clients (sort-by :accountId (clients-with-type :individual ))]
    (page-layout "Individuals" :individuals {}
      [:div {}
       [:a {:href (url-add-individual) :class "btn btn-primary right"} "Add individual"]
       [:br ]
       [:br ]
       (render-clients-list :individuals clients page)]
      )))


(defn render-validate-and-add-individual [client]
  (if (valid-client? client)
    (do
      (insert-client! client)
      (redirect-with-message! (url-list-individuals) (message-success "Individual added!")))
    (render-add-individual client)))


(defn render-edit-individual-by-id [id]
  (render-edit-individual (client-by-id id)))

(defn render-validate-and-edit-individual [client]
  (if (valid-client? client)
    (do
      (update-client! client)
      (redirect-with-message! (url-list-individuals) (message-success "Individual saved!")))
    (render-edit-individual client)))

(defn render-delete-individual [id]
  (do
    (delete-client! (client-by-id id))
    (redirect-with-message! (url-list-individuals) (message-success "Individual deleted!"))))

