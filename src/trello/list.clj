(ns trello.list
  (refer-clojure :exclude [get])
  (:use [trello.client :as client]))

(defn get-list
  "Get a single list"
  ([] ())
  ([id] (client/api-request :get (format "lists/%s" id))))

(defn get-list-cards
  "Returns all cards for a list
   param id is the id of a Trello list"
  [id]
  (client/api-request :get (format "lists/%s/cards" id)))