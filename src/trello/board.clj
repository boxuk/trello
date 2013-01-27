(ns trello.board
  (refer-clojure :exclude [get])
  (:require [trello.client :as client])
  (:use [trello.client :as client]))

(defn all []
  (client/api-request :get (format "Members/my/boards/all")))

(defn get
  "Get Trello boards"
  ([] (all)) ;; all
  ([id] (client/api-request :get (format "boards/%s" id)))
  ([id resource] (client/api-request :get (format "boards/%s/%s" id (name resource)))))

(defn create
  "Create a new Trello board
   - name (required) 
     Valid Values: A string with a length from 1 to 16384
   - desc (optional) 
     Valid Values: A user ID or name
   - idOrganization (optional)
     Valid Values: The id or name of the organization to add the board to.
   - idBoardSource (optional)
     Valid Values: The id of the board to copy into the new board"
  ([name] (client/api-request :post "boards" {:name name}))
  ([name other-params]
   (let [params (merge {:name name} other-params)]
     (client/api-request :post "boards" {:name name}))))

