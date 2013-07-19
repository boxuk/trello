(defproject trello "0.1.1-SNAPSHOT"
  :description "Clojure wrapper for the Trello API"
  :url "http://www.owainlewis.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.3.2"]
                 [cheshire "3.1.0"]
                 [midje "1.3.2-SNAPSHOT"]
                 [clj-http-fake "0.3.0"]
                 [com.stuartsierra/lazytest "1.2.3"]]
  :plugins [[lein-midje "2.0.4"]]
  :repositories {"stuart" "http://stuartsierra.com/maven2"})
