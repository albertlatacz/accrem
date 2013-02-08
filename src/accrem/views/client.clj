(ns accrem.views.client
  (:require [accrem.web :as web])

  (:use accrem.views.common
        noir.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers
        somnium.congomongo
        [somnium.congomongo.config :only [*mongo-config*]]))


(defn valid-client? [client]
  (web/validate-rule
    (web/rule-has-value? (:accountId client))
    [:accountId "Required field"])

  (web/validate-rule
    (web/rule-has-value? (:accountName client))
    [:accountName "Required field"])

  (not (web/validate-errors? :accountId :accountName )))


(defn render-client-name [client]
  (str (:accountName client) " (" (:accountId client) ")"))

(defn render-client-link [client]
  [:a {:href (str "/app/" (:accountType client) "/edit/" (:_id client)) :target "_blank"} (render-client-name client)])


(defn- render-client-links [client]
  [:div {}
   [:div {:align "center"}
    [:a {:class "btn" :href (url-edit-client client)} [:i {:class "icon-edit"}] " Edit"]
    "&nbsp;&nbsp;"
    [:a {:class "btn" :href (url-list-client-tasks (:_id client))} [:i {:class "icon-list"}] " Tasks"]
    "&nbsp;&nbsp;"
    [:a {:class "btn btn-danger" :data-toggle "modal" :href (str "#confirmDelete" (:_id client))} [:i {:class "icon-trash icon-white"}] " Delete"]
    ]

   [:div {:id (str "confirmDelete" (:_id client)) :class "modal hide fade"}
    [:div {:class "modal-header"}
     [:button {:type "button" :class "close" :data-dismiss "modal"} "×"]
     [:h3 (str "Delete " (:accountName client) " (" (:accountId client) ")")]
     ]
    [:div {:class "modal-body"}
     (str "Are you sure you would like to delete " (:accountType client) " " (:accountName client) " (" (:accountId client) ")?")
     ]

    [:div {:class "modal-footer"}
     [:a {:href "#" :class "btn" :data-dismiss "modal"} "Cancel"]
     [:a {:href (url-delete-client client) :class "btn btn-danger"} [:i {:class "icon-trash icon-white"}] " Delete"]]

    ]]
  )


(defn render-client-row [client]
  [:tr {}
   [:td (:accountId client)]
   [:td (:accountName client)]
   [:td (:contactName client)]
   [:td [:a {:href (str "tel:" (:contactTelephone client))} (:contactTelephone client)]]
   [:td [:a {:href (str "mailto:" (:contactEmail client))} (:contactEmail client)]]
   [:td {:width "250"} (render-client-links client)]]
  )

(defn render-clients-list [clientType clients page]
  (render-paged-list clients page
    #(vector "Client ID" "Name" "Contact" "Telephone" "Email" "Action")
    #(render-client-row %)
    #(url-list-clients clientType %)))


(defn client-action-completed-page [client action nextUrl]
  (let [actionText (str "Client " (name action))]
    (page-layout actionText :tasks client
      [:div {:class "well clearfix span6 offset3 row"}
       [:div [:h3 actionText]
        [:hr ]]

       [:div [:p "The following client has been " action]
        [:div {:class "well"}
         [:br ]
         [:p (str "ID: " (:accountId client))]
         [:p (str "Name: " (:accountName client))]
         ]
        [:hr ]
        ]

       [:div {:class "right"}
        [:a {:href nextUrl :class "btn btn-primary"} "Finish"]
        ]
       ])))


(defn render-associated-individual [individual association]
  (let [nodeId (str "associatedIndividualNode" (:_id individual))]
    [:tr {:id nodeId}
     [:td [:div {:class "left" :style "margin:5px"} (render-client-link individual)]

      [:div {:class "right" :style "margin:5px"}
       [:a {:onclick (str "$(\"#" nodeId "\").remove();")
            :class "btn rignt"
            :style "width:30px;align:center"}

        [:i {:class "icon-trash"}]]]

      "&nbsp;&nbsp;&nbsp;"
      [:div {:class "right" :style "margin:5px"}
       (hidden-field "associatedIndividualsIds[]" (:_id individual))
       (drop-down {:class "span2"} "associatedIndividualsTypes[]" '("Director" "Shareholder" "Employee") association)
       ]

      ]]
    )
  )


(defn render-select-individual-to-associate-row [client]
  [:tr {}
   [:td {}
    [:div {:class "left"} (str (:accountName client) " (" (:accountId client) ")")]
    [:div {:class "right"}
     [:a {:onclick (str "$('#associatedIndividuals').append('" (html (render-associated-individual client "Director")) "'.replace(/" (str "associatedIndividualNode" (:_id client)) "/g, 'newAssociatedIndividualNode' + Math.floor(Math.random()*1000000)));")
          :class "btn rignt"
          :style "width:30px;align:center"
          :data-dismiss "modal"}

      [:i {:class "icon-plus"}] "  "]]]])


(defn render-select-individual-to-associate [clients]
  [:div [:a {:class "btn right" :data-toggle "modal" :href "#selectIndividualToAssociate"} "Associate"]
   [:div {:id "selectIndividualToAssociate" :class "modal hide fade"}
    [:div {:class "modal-header"}
     [:button {:type "button" :class "close" :data-dismiss "modal"} "×"]
     [:h3 "Associate with individual"]]

    [:div {:class "modal-body" :style "overflow: auto"}
     [:table {:class "table table-striped table-bordered"}
      (mapcat #(vector (render-select-individual-to-associate-row %)) clients)]]

    [:div {:class "modal-footer"}
     [:a {:href "#" :class "btn" :data-dismiss "modal"} "Cancel"]]]
   ]
  )


