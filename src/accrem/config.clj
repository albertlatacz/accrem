(ns accrem.config)

(def systemName "Greenrock Accountancy")

(defn url-base [] "/app")
(defn url-client-base [] (str (url-base) "/client"))