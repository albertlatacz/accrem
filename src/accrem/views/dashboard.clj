(ns accrem.views.dashboard
  (:use accrem.models.client
        accrem.models.tasks
        accrem.views.common
        accrem.views.client
        accrem.views.tasks
        accrem.views.sections
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers)
  (:require [accrem.web :as web]))

(defn render-dashboard [request]
  (page-layout "All clients" :dashboard request
    [:div {}
     [:div {:align "right"}
      [:a {:href (url-add-company) :class "btn btn-primary"} "Add company"] "&nbsp;&nbsp;"
      [:a {:href (url-add-individual) :class "btn btn-primary"} "Add individual"] "&nbsp;&nbsp;"
      [:a {:href (url-add-sole-trader) :class "btn btn-primary"} "Add sole trader"]
      ]
     [:br ]]))

(defn render-redirect-to-dashboard []
  (web/redirect "/app/dashboard"))