(ns accrem.views.soletrader
  (:use accrem.models.client
        accrem.views.common
        accrem.views.client
        accrem.views.sections
        noir.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers))

(defn soletrader-fields [soletrader]
  [:div {:class "row"}
   [:div {:class "span6"}
    (account-details-section :soletrader soletrader)
    (personal-details-section soletrader)
    (self-assessment-section soletrader)
    (self-employment-section soletrader)
    ]

   [:div {:class "span6"}
    (client-contact-section soletrader)
    (bank-account-section soletrader)
    (service-plan-section soletrader)
    ]]
  )

(defn sole-trader-details-page [title formSubmitButton formLink request]
  (page-layout title :soletraders {}
    (form-to {:class "form-horizontal"} [:post formLink]
      (soletrader-fields request)
      [:div {:class "well"}
       (submit-button {:class "btn btn-primary right"} formSubmitButton)
       ])))


(defn render-add-sole-trader [client]
  (sole-trader-details-page "Add" "Add sole trader" (url-add-sole-trader) client))

(defn render-edit-sole-trader [client]
  (sole-trader-details-page "Edit" "Save sole trader" (url-edit-sole-trader (:_id client)) client))


(defn render-list-sole-traders [pageId]
  (let [page (. Integer valueOf pageId)
        clients (sort-by :accountId (clients-with-type :soletrader ))]
    (page-layout "Sole traders" :soletraders {}
      [:div {}
       [:a {:href (url-add-sole-trader) :class "btn btn-primary right"} "Add sole trader"]
       [:br ]
       [:br ]
       (render-clients-list :soletraders clients page)]
      )))

(defn render-validate-and-add-sole-trader [client]
  (if (valid-client? client)
    (do
      (insert-client! client)
      (redirect-with-message! (url-list-sole-traders) (message-success "Sole trader added!")))
    (render-add-sole-trader client)))

(defn render-edit-sole-trader-by-id [id]
  (render-edit-sole-trader (client-by-id id)))

(defn render-validate-and-edit-sole-trader [client]
  (if (valid-client? client)
    (do
      (update-client! client)
      (redirect-with-message! (url-list-sole-traders) (message-success "Sole trader saved!")))
    (render-edit-sole-trader client)))

(defn render-delete-sole-trader [id]
  (let [client (client-by-id id)]
    (do
      (delete-client! (client-by-id id))
      (redirect-with-message! (url-list-sole-traders) (message-success "Sole trader deleted!")))))

