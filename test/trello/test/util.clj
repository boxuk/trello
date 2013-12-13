
(ns trello.test.util
  (:use trello.util
        midje.sweet))

(facts "about normalising requests"
  (normalize-http-request "foo/bar") => "foo/bar"
  (normalize-http-request "/foo/bar") => "foo/bar")

