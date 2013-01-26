(ns trello.board
  (refer-clojure :exclude [get])
  (:require [trello.client :as client])
  (:use [trello.client :as client]))

;; Board information

(defn all-boards []
  (client/api-request :get (format "Members/my/boards/all")))

(defn get
  "Get Trello boards"
  ([] (all-boards)) ;; all
  ([id] (client/api-request :get (format "boards/%s" id)))
  ([id resource] (client/api-request :get (format "boards/%s/%s" id (name resource)))))

(defn create!
  "Create a new Trello board"
  )
