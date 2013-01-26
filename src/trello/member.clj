(ns trello.member
  (:use [trello.client :as client]))

(defn member
  "Returns all the information about the specified user,
   or the current user if none specified."
  ([] (member "me"))
  ([id] (client/api-request :get (format "members/%s" id)))
  ([id param] (client/api-request :get (format "members/%s/%s" id param))))