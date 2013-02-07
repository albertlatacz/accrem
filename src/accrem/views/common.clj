(ns accrem.views.common
  (:require [noir.validation :as vali]
            [noir.session :as session]
            [noir.response :as response])

  (:use accrem.config
        accrem.date
        noir.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers))


(defn url-login [] "/login")
(defn url-logout [] "/logout")

(defn url-setup [] "/admin/setup")

(defn url-dashboard [] "/app/dashboard")

(defn url-add-company [] "/app/company/add")
(defn url-edit-company [id] (str "/app/company/edit/" id))
(defn url-delete-company [id] (str "/app/company/delete/" id))
(defn url-list-companies
  ([] (str "/app/companies/1"))
  ([page] (str "/app/companies/" page)))

(defn url-add-individual [] "/app/individual/add")
(defn url-edit-individual [id] (str "/app/individual/edit/" id))
(defn url-delete-individual [id] (str "/app/individual/delete/" id))
(defn url-list-individuals
  ([] (str "/app/individuals/1"))
  ([page] (str "/app/individuals/" page)))

(defn url-add-sole-trader []"/app/soletrader/add")
(defn url-edit-sole-trader [id] (str "/app/soletrader/edit/" id))
(defn url-delete-sole-trader [id] (str "/app/soletrader/delete/" id))
(defn url-list-sole-traders
  ([] (str "/app/soletraders/1"))
  ([page] (str "/app/soletraders/" page)))

(defn url-edit-client [client]
  (case (keyword (:accountType client))
    :company (url-edit-company (:_id client))
    :individual (url-edit-individual (:_id client))
    :soletrader (url-edit-sole-trader (:_id client))
  ))

(defn url-delete-client [client]
  (case (keyword (:accountType client))
    :company (url-delete-company (:_id client))
    :individual (url-delete-individual (:_id client))
    :soletrader (url-delete-sole-trader (:_id client))
  ))



(defn map-tag [tag xs]
  (map (fn [x] [tag x]) xs))



(defn url-add-client-task
  ([] (url-add-client-task ":clientId"))
  ([clientId] (str (url-client-base) "/" clientId "/task/add"))
  )

(defn url-edit-client-task
  ([] (url-edit-client-task ":taskId"))
  ([taskId] (str (url-client-base) "/task/edit/" taskId))
  )


(defn url-delete-client-task
  ([] (url-delete-client-task ":taskId"))
  ([taskId] (str (url-client-base) "/task/delete/" taskId))
  )

(defn url-list-client-tasks
  ([] (url-list-client-tasks ":clientId" ":pageId"))
  ([clientId] (url-list-client-tasks clientId "1"))
  ([clientId pageId] (str (url-client-base) "/" clientId "/tasks/" pageId))
  )

(defn url-list-clients
  ([] (url-list-clients ":clientType" ":pageId"))
  ([clientType] (url-list-clients (name clientType) ":pageId"))
  ([clientType pageId] (str (url-base) "/" (name clientType) "/" pageId))
  )

(defn url-add-client
  ([clientType] (str (url-base) "/" (name clientType) "/add"))
  )


(defn admin? []
  (session/get :admin ))


(defn logged-in? []
  (session/get :username ))

(defn me []
  (session/get :username ))

(defn menu-navigation-link [link name active]
  (if (true? active)
    [:li {:class "active"} [:a {:href link} name]]
    [:li [:a {:href link} name]]))

(defn main-menu [active]
  [:div {:class "navbar navbar-fixed-top"}
   [:div {:class "navbar-inner"}
    [:div {:class "container"}
     [:a {:href "/" :class "brand"} systemName]
     [:div {:class "nav-collapse"}
      [:ul {:class "nav"}
       (menu-navigation-link (url-dashboard) "Dashboard" (= active :dashboard ))
       (menu-navigation-link (url-list-companies) "Companies" (= active :companies ))
       (menu-navigation-link (url-list-sole-traders) "Sole Traders" (= active :soletraders ))
       (menu-navigation-link (url-list-individuals) "Individuals" (= active :individuals ))
       ]

      [:ul {:class "nav pull-right"}
       (if (logged-in?)
         (menu-navigation-link "/logout" (str "Logout (" (me) ")") (= active :login ))
         (menu-navigation-link "/login" "Login" (= active :login )))]

      ]]]])





(defn main-menu-tabs [active]
  [:ul {:class "nav nav-tabs navbar-fixed-top"}
   (menu-navigation-link (url-dashboard) "Dashboard" (= active :dashboard ))
   (menu-navigation-link (url-list-companies) "Companies" (= active :companies ))
   (menu-navigation-link (url-list-sole-traders) "Sole Traders" (= active :soletraders ))
   (menu-navigation-link (url-list-individuals) "Individuals" (= active :individuals ))
   ])



(defn alert-message [text type]
  {:alertMessage text :alertType type})

(defn message-error [text]
  (alert-message text "error"))

(defn message-success [text]
  (alert-message text "success"))

(defn message-info [text]
  (alert-message text "info"))

(defn redirect-with-message! [url message]
  (session/flash-put! message)
  (response/redirect url))

(defn render-alert-message []
  (let [message (session/flash-get)]
    (if (:alertType message)
      [:div [:div {:class (str "alert " "alert-" (:alertType message))}
             [:a {:class "close" :data-dismiss "alert" :href "#"} "×"]
             [:h4 (:alertMessage message)]]])))


(defn- render-paged-list-bar-link [activePage entry linkRenderer]
  (let [entryPage (first entry)
        pageLink (linkRenderer entryPage)]
    (if (= activePage entryPage)
      [:li {:class "active"} [:a {:href pageLink} entryPage]]
      [:li [:a {:href pageLink} entryPage]])
    ))

(defn render-paged-list [items page headerRenderer itemRenderer pageLinkRenderer]
  (let [pagedTasks (zipmap (range 1 Double/POSITIVE_INFINITY) (partition-all 10 items))
        currentPage (pagedTasks page)
        numberOfPages (count pagedTasks)
        canPageBack (not (= page 1))
        canPageForward (not (= page numberOfPages))
        previousPage (if canPageBack (- page 1) page)
        nextPage (if canPageForward (+ page 1) page)
        ]
    [:div {}
     [:table {:class "table table-striped table-bordered"}
      [:thead (map-tag :th (headerRenderer))]
      (mapcat #(vector (itemRenderer %)) currentPage)]

     (when (> numberOfPages 1)
       [:div {:class "pagination pagination-right"}
        [:ul {}
         [:li {:class (when-not canPageBack "disabled")}
          [:a {:href (pageLinkRenderer previousPage)} "«"]]

         (mapcat #(vector (render-paged-list-bar-link page % pageLinkRenderer)) (reverse pagedTasks))

         [:li {:class (when-not canPageForward "disabled")}
          [:a {:href (pageLinkRenderer nextPage)} "»"]]
         ]])
     ]))

(defn page-layout [title page request & content]
  (html5
    [:head [:title (str systemName " > " title)]
     (include-css "/css/reset.css")
     (include-css "/css/bootstrap.css")
     (include-css "/css/style.css")
     (include-css "/css/bootstrap-responsive.css")
     (include-css "/css/datepicker.css")
     (include-css "/css/DT_bootstrap.css")

     (include-js "/js/jquery.js")
     (include-js "/js/jquery.dataTables.js")
     (include-js "/js/DT_bootstrap.js")
     ]

    [:body ;(main-menu page)
     (main-menu page)

     [:div {:class "container"}
      (render-alert-message)
      content
      ]

     (include-js "/js/bootstrap-datepicker.js")
     (include-js "/js/bootstrap-transition.js")
     (include-js "/js/bootstrap-alert.js")
     (include-js "/js/bootstrap-modal.js")
     (include-js "/js/bootstrap-dropdown.js")
     (include-js "/js/bootstrap-scrollspy.js")
     (include-js "/js/bootstrap-tab.js")
     (include-js "/js/bootstrap-tooltip.js")
     (include-js "/js/bootstrap-popover.js")
     (include-js "/js/bootstrap-button.js")
     (include-js "/js/bootstrap-collapse.js")
     (include-js "/js/bootstrap-carousel.js")
     (include-js "/js/bootstrap-typeahead.js")

     ]))

(defn json-response
  [content]
  {:headers {"Content-Type" "application/json"}
   :body content})


(defn error-item [[first-error]]
  [:span first-error])


(defn field [fieldId labelText content]
  [:div {:class "control-group"}
   (label {:class "control-label"} (name fieldId) labelText)
   [:div {:class "controls"}
    content
    (vali/on-error fieldId error-item)]])

(defn field-hidden [fieldId value]
  (hidden-field (name fieldId) value))

(defn field-text [fieldId text value]
  (field fieldId text (text-field {:class "span4"} (name fieldId) value)))

(defn field-password [fieldId text value]
  (field fieldId text (password-field {:class "span4"} (name fieldId) value)))

(defn field-email [fieldId text value]
  (field fieldId text (text-field {:class "span4" :type "email"} (name fieldId) value)))

(defn field-text-area [fieldId text value]
  (field fieldId text (text-area {:class "span4" :rows "5"} (name fieldId) value)))

(defn field-dropdown [fieldId text options selected]
  (field fieldId text (drop-down {:class "span4"} (name fieldId) options selected)))

(defn field-date-with-default [fieldId text value default]
  (let [componentId (str (name fieldId) "Component")
        dateOrDefault (if (nil? value) default value)]
    (field fieldId text
      [:div {:id componentId :class "input-append date" :data-date (if (empty? dateOrDefault) (format-date (now) "dd-MM-yyyy") dateOrDefault) :data-date-format "dd-mm-yyyy"}
       [:input {:id (name fieldId) :name (name fieldId) :type "text" :placeholder "dd-mm-yyyy" :style "width:330px" :value dateOrDefault}]
       [:span {:class "add-on"} [:i {:class "icon-th"}]]
       [:h (str "<script type=\"text/javascript\">$(function(){$('#" componentId "').datepicker({format: 'dd-mm-yyyy'}); });</script>")]
       ])))

(defn field-date [fieldId text value]
  (field-date-with-default fieldId text value ""))

(defn
  page-section [title content]
  [:div {:class "well clearfix"}
   [:h3 title]
   [:hr ]
   content
   ])
