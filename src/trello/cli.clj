(ns trello.cli
 (:require [trello.core :as core]
           [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(defonce auth (core/settings "config.clj"))

(defn print-boards 
  "Print out all the Trello boards"
  []
  (let [boards (core/boards auth)]
    (doseq [board boards]
      (->>
        ((juxt :id :name) board)
        (interpose " ")
        (apply str)
        println))))

;; ************************************************************
;; Command line client
;; ************************************************************

(def cli-options
  [["-k" "--key KEY" "Trello Key"]
   ["-t" "--token TOKEN" "Trello Token"]])

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (clojure.string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn usage [options-summary]
  (->> ["Trello CLI"
        ""
        "Usage: lein run [opts] action"
        ""
        "Options:"
          options-summary
        ""
        "Actions:"
        "  boards   List all Trello boards"
        ""]
       (clojure.string/join \newline)))

(defn print-json 
  "Helper function for printing data"
  [json & keys]
  (let [results ((apply juxt (into [] keys)) json)]
    (doseq [result results]
      (println result))))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    ;; Handle help and error conditions
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors)))
    ;; Execute program with options
    (case (first arguments)
      "boards"  (print-boards)
      "profile" (println "My profile")
      (exit 1 (usage summary)))))
