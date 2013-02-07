(ns accrem.views.user
  (:require [clojib.encrypt :as crypt]
            [noir.validation :as vali]
            [noir.session :as session]
            [clojure.string :as string])

  (:use accrem.models.user
        accrem.views.common
        noir.core
        [noir.response :only [redirect]]
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers))

(defn prepare [{password :password :as user}]
  (assoc user :password (crypt/encrypt-password password)))

(defn valid-user? [username]
  (vali/rule (not (user-by-name username))
    [:username "That username is already taken"])

  (vali/rule (vali/min-length? username 3)
    [:username "Username must be at least 3 characters."])
  (not (vali/errors? :username :password )))

(defn valid-psw? [password]
  (vali/rule (vali/min-length? password 5)
    [:password "Password must be at least 5 characters."])
  (not (vali/errors? :password )))


(defn login! [user]
  (let [stored-pass (:password (user-by-name (:username user)))]
    (if (and stored-pass (crypt/passwords= (:password user) stored-pass))
      (do
        (session/put! :admin (= "admin" (:username user)))
        (session/put! :username (:username user)))
      (vali/set-error :login-status "Invalid username or password"))))


(defn render-validate-and-login [user]
  (if (login! user)
    (redirect "/app/dashboard")
    (render "/login" user)))

(defn render-login []
  (page-layout "Welcome! Please login..." :login {}
    [:div {:class "row"}
     [:div {:class "well clearfix span6 offset3"}
      [:h3 "Login"]
      [:hr ]

      (form-to {:class "form-horizontal"} [:post "/login"]
        [:div {}
         (field-text :username "Username" "")
         (field-password :password "Password" "")
         (vali/on-error :login-status error-item)
         (submit-button {:class "btn btn-primary right"} "Login")
         ])]]))


(defn render-logout []
  (session/clear!)
  (redirect "/"))

(defn add! [{:keys [username password] :as user}]
  (when (valid-user? username)
    (when (valid-psw? password)
      (do
        (println (str "added" user))
        (insert-user! (prepare user))
        )
      )))

(defn redirect->authentication []
  (when-not (logged-in?)
    (redirect (url-login))))

(defn redirect->setup[]
  (when-not (initialised?)
    (redirect (url-setup))))