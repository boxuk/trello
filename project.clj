(defproject trello "0.1.1-SNAPSHOT"
  :description "Clojure wrapper for the Trello API"
  :url "http://www.owainlewis.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.3.0"]					
                 [clj-http "0.3.2"]
                 [cheshire "3.1.0"]]
  :dev-dependencies [[midje "1.3.1"]
                     [lein-midje "1.0.9"]
                     [clj-http-fake "0.3.0"]
                     [jonase/eastwood "0.0.2"]
                     [com.stuartsierra/lazytest "1.2.3"]
                     [lein-marginalia "0.7.0"]]
  :repositories {"stuart" "http://stuartsierra.com/maven2"})
