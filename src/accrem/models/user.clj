(ns accrem.models.user
  (:require [accrem.storage :as storage]
            [clojib.encrypt :as crypt]))

(defn all-users []
  (storage/fetch-all :users))

(defn user-by-id [id]
  (storage/fetch-by-id :users id))

(defn user-by-name [name]
  (first
    (storage/fetch-where :users {:username name})))

(defn insert-user! [user]
  (storage/insert! :users user))

(defn update-user! [user]
  (storage/update! :users user))

(defn delete-user! [user]
  (storage/delete! :users user))

(defn delete-all-users! []
  (storage/drop-collection! :users))

(defn initialised? []
  (storage/collection-exists? :users))

(defn encrypt-user-password [{password :password :as user}]
  (assoc user :password (crypt/encrypt-password password)))
