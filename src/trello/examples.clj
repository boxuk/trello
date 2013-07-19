(ns trello.examples
  (:require [trello.core]))

;; Get all boards

(def auth (auth-map-from-settings))

(def all-boards (board-all auth))



