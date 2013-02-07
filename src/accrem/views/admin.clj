(ns accrem.views.admin
  (:use accrem.models.user
        accrem.views.common
        accrem.views.sections
        noir.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers)
  (:require [accrem.models.user :as users]
            [noir.validation :as vali]
            [clojib.encrypt :as crypt]))


(defn render-setup-page [title formLink request]
  (when-not (users/initialised?)
    (page-layout "Setup" :setup {}
      (form-to {:class "form-horizontal"} [:post formLink]
        [:div {:class "row"}
         [:div {:class "span6 offset3"}
          (page-section "Please enter password for \"admin\""
            [:div {}
             (field-hidden :username "admin")
             (field-password :password "Password" (:password request))
             (field-password :password-repeat "Repeat password" (:password-repeat request))
             (submit-button {:class "btn btn-primary right"} "Save")
             ])]]
        ))))

(defn render-setup [setup]
  (render-setup-page "Setup" (url-setup) setup))

(defn valid-setup? [setup]
  (vali/rule (vali/min-length? (:password setup) 6)
    [:password "password too short (at least 6 characters required)"])

  (vali/rule (= (:password setup) (:password-repeat setup))
    [:password-repeat "password and repeated password do not match"])

  (not (vali/errors? :password :password-repeat )))


(defn render-validate-and-save-setup [setup]
  (if (valid-setup? setup)
    (do
      (insert-user! {:username (:username setup) :password (crypt/encrypt-password (:password setup))})
      (redirect-with-message! (url-dashboard) (message-success "Password set for admin.")))
    (render-setup setup)))