(ns accrem.server
  (:require [noir.server :as server]
            [accrem.models.user :as users]))

(server/load-views "src/accrem/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev ))
        port (Integer. (get (System/getenv) "PORT" "8085"))]

    (server/start port {:mode mode
                        :ns 'accrem})))

