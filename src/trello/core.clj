(ns trello.core
  (:require [trello.client :as api]))

;;; General Requests 

(defn member
  "Returns all the information about the specified user,
   or the current user if none specified"
  ([] (member "me"))
  ([id] (api/api-request :get (str "members/" id))))

(defn get-all-query
  "Perform a query that returns all results for a given search term q"
  [q]
  (api/api-request :get (format "members/my/%s/all" q)))
  
(defn get-all [item]
  "Return all results for item. Item can be either a string or keyword"
  (let [query (if-not (keyword? item) (keyword item) item)]
  (condp = query
    :boards (get-all-query "boards")
    :cards (get-all-query "cards")
    :organizations (get-all-query "organizations")
    (prn (format "Not such item %s" item)))))
  
;;; Boards

(defn get-board
  "Get a single Trello board"
  ([] (get-all :boards))
  ([id] (api/api-request :get (format "boards/%s/lists" id))))

(defn -main
  [& args] )
