(ns accrem.storage
  (:require [somnium.congomongo :as congo])
  (:use [somnium.congomongo.config :only [*mongo-config*]]))


(def mongo-url
  (or (get (System/getenv) "MONGOHQ_URL") (System/getProperty "MONGOHQ_URL")))


(defn- split-mongo-url [url]
  "Parses mongodb url from heroku, eg. mongodb://user:pass@localhost:1234/db"
  (let [matcher (re-matcher #"^.*://(.*?):(.*?)@(.*?):(\d+)/(.*)$" url)] ;; Setup the regex.
    (when (.find matcher) ;; Check if it matches.
      (zipmap [:match :user :pass :host :port :db ] (re-groups matcher))))) ;; Construct an options map.




(defn- maybe-init []
  "Checks if connection and collection exist, otherwise initialize."
  (when (not (congo/connection? *mongo-config*)) ;; If global connection doesn't exist yet.
    (let [config (split-mongo-url mongo-url)] ;; Extract options.
      (println "Initializing mongo @ " mongo-url)
      (congo/mongo! :db (:db config) :host (:host config) :port (Integer. (:port config))) ;; Setup global mongo.
      (congo/authenticate (:user config) (:pass config)) ;; Setup u/p.
      (or (congo/collection-exists? :firstcollection ) ;; Create collection named 'firstcollection' if it doesn't exist.
        (congo/create-collection! :firstcollection )))))

(defn fetch-all [collection]
  (maybe-init)
  (congo/fetch collection))

(defn fetch-by-id [collection id]
  (maybe-init)
  (congo/fetch-by-id collection (congo/object-id id)))

(defn fetch-where [collection pattern]
  (maybe-init)
  (congo/fetch collection :where pattern))

(defn update! [collection entity]
  (maybe-init)
  (congo/update! collection (fetch-by-id collection (:_id entity))
    (assoc entity :_id (congo/object-id (:_id entity)))))

(defn insert! [collection entity]
  (maybe-init)
  (congo/insert! collection (dissoc entity :_id )))

(defn delete! [collection entity]
  (maybe-init)
  (congo/destroy! collection entity))

(defn collection-exists? [collection]
  (maybe-init)
  (congo/collection-exists? collection))

(defn drop-collection! [collection]
  (maybe-init)
  (congo/drop-coll! collection))

