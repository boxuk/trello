(ns trello.test.client
  (:use trello.client
        trello.test.helpers
        midje.sweet))

(facts "about normalising requests"
  (#'trello.client/normalize-request "foo/bar") => "foo/bar"
  (#'trello.client/normalize-request "/foo/bar") => "foo/bar")

(facts "about generating urls"
  (#'trello.client/generate-url "foo" "" "" {:baz ["qwe" "rty"]}) => (contains "baz=qwe,rty")
  (#'trello.client/generate-url "foo" "x" "y" {:baz "boo"}) => "https://api.trello.com/1/foo?key=x&token=y&baz=boo"
  (#'trello.client/generate-url "foo" "x" "y") => "https://api.trello.com/1/foo?key=x&token=y")

(with-fake-api
  (facts "about passing arbitrary parameters when making requests"
    (api-request :get "members/me" {:fields ["bazzle"]}) 
      => (map-containing {:bazzle 1}))
)

