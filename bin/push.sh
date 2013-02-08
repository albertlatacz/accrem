#!/bin/bash

echo "(def settings \
      {:deploy-repositories \
        {\"clojars-https\" \
            {:url \"https://clojars.org/repo\" \
             :username \"albertlatacz\" \
             :password \"$CLOJARS_AUTH\"}}})" > ~/.lein/init.clj



lein deploy clojars-https

rm ~/.lein/init.clj