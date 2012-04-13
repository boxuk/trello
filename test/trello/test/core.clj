(ns trello.test.core
  (:use trello.core
        clj-http.fake
        midje.sweet))

(defn- json 
  "Response with specified JSON string"
  [json-string]
  (fn [req] {:status 200 :headers {} :body json-string}))

(def test-routes
  {
    #".*members\/foo.*" (json "{\"name\": \"foo\"}")
    #".*members\/me.*" (json "{\"name\": \"tester\"}")
  })

(defmacro with-fake-api [& body]
  `(binding [auth-key "" auth-token ""]
     (with-fake-routes test-routes
       (do ~@body))))

(facts "about normalising requests"
  (normalize-request "foo/bar") => "foo/bar"
  (normalize-request "/foo/bar") => "foo/bar")

(facts "about generating query strings"
  (generate-query-string "foo" "x" "y" {:baz "boo"}) => "https://api.trello.com/1/foo?key=x&token=y&baz=boo"
  (generate-query-string "foo" "x" "y") => "https://api.trello.com/1/foo?key=x&token=y")

(with-fake-api
  (facts "about member requests"
    (member "foo") => (map-containing {:name "foo"})
    (member) => (map-containing {:name "tester"}))
)

