(ns trello.test.core
  (:use trello.core
        clj-http.fake
        trello.test.helpers
        midje.sweet))

(with-fake-api
  (facts "about member requests"
    (member "foo") => (map-containing {:name "foo"})
    (member) => (map-containing {:name "tester"}))
)