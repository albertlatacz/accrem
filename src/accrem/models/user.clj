(ns accrem.models.user
  (:require [clojib.encrypt :as crypt])
  (:use accrem.storage))

(defn all-users []
  (fetch-all :users))

(defn user-by-id [id]
  (fetch-by-id :users id))

(defn user-by-name [name]
  (first
    (fetch-where :users {:username name})))

(defn insert-user! [user]
  (insert! :users user))

(defn update-user! [user]
  (update! :users user))

(defn delete-user! [user]
  (delete! :users user))

(defn delete-all-users! []
  (drop-collection! :users))

(defn initialised? []
  (collection-exists? :users))

(defn encrypt-user-password [{password :password :as user}]
  (assoc user :password (crypt/encrypt-password password)))
