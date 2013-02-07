(ns accrem.models.tasks
  (:use accrem.storage))

(defn all-tasks []
  (fetch-all :tasks ))

(defn task-by-id [id]
  (fetch-by-id :tasks id))

(defn tasks-for-client [clientId]
  (fetch-where :tasks {:clientId clientId}))

(defn insert-task! [task]
  (insert! :tasks task))

(defn update-task! [task]
  (update! :tasks task))

(defn delete-task! [task]
  (delete! :tasks task))

(defn delete-all-tasks! []
  (drop-collection! :tasks))

