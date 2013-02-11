(ns accrem.views.company
  (:require [accrem.web :as web])
  (:use accrem.models.client
        accrem.views.client
        accrem.views.common
        accrem.views.sections
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers))

(defn company-fields [company]
  [:div {:class "row"}
   [:div {:class "span6"}
    (account-details-section :company company)
    (associated-individuals-section company)
    (company-details-section company)
    (payroll-section company)
    (vat-details-section company)
    ]

   [:div {:class "span6"}
    (client-contact-section company)
    (bank-account-section company)
    (annual-accounts-section company)
    (service-plan-section company)
    ]]
  )

(defn company-details-page [title formSubmitButton formLink company]
  (page-layout title :companies {}
    (form-to {:class "form-horizontal"} [:post formLink]
      (company-fields company)
      [:div {:class "well"}
       (submit-button {:class "btn btn-primary right"} formSubmitButton)
       ])))


(defn render-add-company [client]
  (company-details-page "Add" "Add company" (url-add-company) client))

(defn render-edit-company [client]
  (company-details-page "Edit" "Save company" (url-edit-company (:_id client)) client))

(defn render-edit-company-by-id [id]
  (render-edit-company (client-by-id id)))

(defn render-list-companies [pageId]
  (let [page (. Integer valueOf pageId)
        clients (sort-by :accountId (clients-with-type :company ))]
    (page-layout "Companies" :companies {}
      [:div {}
       [:a {:href (url-add-company) :class "btn btn-primary right"} "Add"]
       [:br ]
       [:br ]
       (render-clients-list :companies clients page)]
      )))


(defn render-validate-and-add-company [client]
  (if (valid-client? client)
    (do
      (insert-client! client)
      (redirect-with-message! (url-list-companies) (message-success "Company added!"))
      )
    (render-add-company client)))



(defn render-validate-and-edit-company [client]
  (if (valid-client? client)
    (do
      (update-client! client)
      (redirect-with-message! (url-list-companies) (message-success "Company saved!")))
    (render-edit-company client)))



(defn render-delete-company [id]
  (let [client (client-by-id id)]
    (do
      (delete-client! (client-by-id id))
      (redirect-with-message! (url-list-companies) (message-success "Company deleted!")))))
