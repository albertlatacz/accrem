(ns accrem.models.client
  (:require [accrem.storage :as storage]))

(defn all-clients []
  (storage/fetch-all :clients ))

(defn client-by-id [id]
  (storage/fetch-by-id :clients id))

(defn clients-with-type [clientType]
  (storage/fetch-where :clients {:accountType clientType}))

(defn insert-client! [client]
  (storage/insert! :clients client))

(defn update-client! [client]
  (storage/update! :clients client))

(defn delete-client! [client]
  (storage/delete! :clients client))

(defn delete-all-clients! []
  (storage/drop-collection! :clients ))

