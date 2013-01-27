(ns trello.card
  (refer-clojure :exclude [get])
  (:use [trello.client :as client]))

(defn get-card
  "Get a single card"
  [id]
  (client/api-request :get (format "cards/%s" id)))

