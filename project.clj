(defproject accrem "0.2.0-SNAPSHOT"
  :description "FIXME: write this!"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [congomongo "0.1.5-SNAPSHOT"]
                 [clojib "11"]
                 [noir "1.2.1"]
                 ]
  :dev-dependencies [[midje "1.4.0"]]
  :jvm-opts ["-DMONGOHQ_URL=mongodb://:@localhost:5555/db"]
  :repositories {"stuart" "http://stuartsierra.com/maven2"}
  :main accrem.server
  :profiles {:dev {:plugins [[lein-midje "2.0.0-SNAPSHOT"]]}}
  )