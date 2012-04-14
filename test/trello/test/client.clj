(ns trello.test.client
  (:use trello.client
        trello.test.helpers
        midje.sweet))

(facts "about normalising requests"
  (normalize-request "foo/bar") => "foo/bar"
  (normalize-request "/foo/bar") => "foo/bar")

(facts "about generating urls"
  (generate-url "foo" "" "" {:baz ["qwe" "rty"]}) => (contains "baz=qwe,rty")
  (generate-url "foo" "x" "y" {:baz "boo"}) => "https://api.trello.com/1/foo?key=x&token=y&baz=boo"
  (generate-url "foo" "x" "y") => "https://api.trello.com/1/foo?key=x&token=y")
