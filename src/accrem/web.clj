(ns accrem.web
  (:require [noir.response :as response]
            [noir.session :as session]
            [noir.validation :as validate]))

(defn redirect [url]
  (response/redirect url))

(defn session-get [k]
  (session/get k))

(defn session-put! [k v]
  (session/put! k v))

(defn session-clear! []
  (session/clear!))

(defn session-flash-put! [v]
  (session/flash-put! v))

(defn session-flash-get []
  (session/flash-get))

(defn validate-rule [pred [field error]]
  (validate/rule pred [field error]))

(defn rule-has-value? [v]
  (validate/has-value? v))

(defn rule-min-length? [v len]
  (validate/min-length? v len))

(defn validate-errors? [& field]
  (validate/errors? field))

(defn validate-set-error [field error]
  (validate/set-error field error))

(defn validate-on-error [field func]
  (validate/on-error field func))

