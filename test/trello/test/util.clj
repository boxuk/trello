(ns trello.test.util
  (:use trello.util
        midje.sweet))

(facts "about normalising requests"
  (normalize-http-request "foo/bar") => "foo/bar"
  (normalize-http-request "/foo/bar") => "foo/bar")

;; (facts "about generating urls"
;;   (#'trello.client/generate-url "foo" "" "" {:baz ["qwe" "rty"]}) => (contains "baz=qwe,rty")
;;   (#'trello.client/generate-url "foo" "x" "y" {:baz "boo"}) => "https://api.trello.com/1/foo?key=x&token=y&baz=boo"
;;   (#'trello.client/generate-url "foo" "x" "y") => "https://api.trello.com/1/foo?key=x&token=y")

;; (with-fake-api
;;   (facts "about passing arbitrary parameters when making requests"
;;     (api-request :get "members/me" {:fields ["bazzle"]})
;;       => (map-containing {:bazzle 1}))
;; )

