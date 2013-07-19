(ns trello.member
  (refer-clojure :exclude [get])
  (:use [trello.client :as client]))

(defn me
  "Return the Trello profile for the current user"
  [auth]
  (client/request auth :get "/members/me"))

