(ns accrem.models.client
  (:use accrem.storage))

(defn all-clients []
  (fetch-all :clients ))

(defn client-by-id [id]
  (fetch-by-id :clients id))

(defn clients-with-type [clientType]
  (fetch-where :clients {:accountType clientType}))

(defn insert-client! [client]
  (insert! :clients client))

(defn update-client! [client]
  (update! :clients client))

(defn delete-client! [client]
  (delete! :clients client))

(defn delete-all-clients! []
  (drop-collection! :clients ))

