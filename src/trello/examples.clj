(ns trello.examples
  (:require [trello.client :as client]
            [trello.board :as board]))

;; Get all boards

(def auth (client/auth-map-from-settings))

(def all-boards (boards/all auth))



