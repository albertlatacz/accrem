(def build-number (or (System/getenv "ACCREM_BUILD_NUMBER") "DEV-SNAPSHOT"))
(defproject accrem build-number
  :description "Simple accounting practice client management website written in Clojure"
  :url "https://github.com/albertlatacz/accrem"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [congomongo "0.2.0"]
                 [clojib "11"]
                 [noir "1.2.1"]
                 ]
  :dev-dependencies [[midje "1.4.0"]]
  :jvm-opts ["-DMONGOHQ_URL=mongodb://:@localhost:5555/db"]
  :repositories {"stuart" "http://stuartsierra.com/maven2"}
  :main accrem.server
  :profiles {:dev {:plugins [[lein-midje "2.0.0-SNAPSHOT"]]}}
  )