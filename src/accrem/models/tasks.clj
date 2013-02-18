(ns accrem.models.tasks
  (:require [accrem.storage :as storage]))

(defn all-tasks []
  (storage/fetch-all :tasks ))

(defn task-by-id [id]
  (storage/fetch-by-id :tasks id))

(defn tasks-for-client [clientId]
  (storage/fetch-where :tasks {:clientId clientId}))

(defn tasks-due []
  (all-tasks))

(defn insert-task! [task]
  (storage/insert! :tasks task))

(defn update-task! [task]
  (storage/update! :tasks task))

(defn delete-task! [task]
  (storage/delete! :tasks task))

(defn delete-all-tasks! []
  (storage/drop-collection! :tasks))

