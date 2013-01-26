(ns trello.core
  (:require [trello.client :as api]
            [trello.util :as util]))

;;; General Requests 

(defn member
  "Returns all the information about the specified user,
   or the current user if none specified."
  ([] (member "me"))
  ([id] (api/api-request :get (format "members/%s" id)))
  ([id param] (api/api-request :get (format "members/%s/%s" id param))))

(defn get-all-query
  "Perform a query that returns all results for a given search term q"
  [q]
  (api/api-request :get (format "members/my/%s/all" q)))
  
(defn get-all
  "Return all results for item. Item can be either a string or keyword"
  [item]
  (let [query (if-not (keyword? item) (keyword item) item)]
  (condp = query
    :boards (get-all-query "boards")
    :cards (get-all-query "cards")
    :organizations (get-all-query "organizations")
    (prn (format "Not such item %s" item)))))

(defn all-boards [] (get-all :boards))

(defn get-board
  "Get a single Trello board"
  ([] (get-all :boards))
  ([id] (api/api-request :get (format "boards/%s" id))))

(defn all-lists-for-board
  "Return all lists for a given board
   id is a board id"
  [id]
  (api/api-request :get (format "boards/%s/lists" id)))

(defn get-all-list-names-for-board
  "Return an array of list names for a board"
  [idx]
  (map (fn [b] (get b :name)) (all-lists-for-board idx)))

;; Lists
;; 
;; Get a specific list
;; Get all lists
;; Get all cards for a list
;;

(defn get-list
  "Get a single list"
  ([] ())
  ([id] (api/api-request :get (format "lists/%s" id))))

(defn get-list-cards
  "Returns all cards for a list
   param id is the id of a Trello list"
  [id]
  (api/api-request :get (format "lists/%s/cards" id)))

;; Cards
;; ==========================
;; Get a single card

(defn get-card
  "Get a single card"
  [id]
  (api/api-request :get (format "cards/%s" id)))