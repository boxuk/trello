(ns trello.test.core
  (:use trello.core
        clj-http.fake
        midje.sweet))

(defn- json 
  "Response with specified JSON string"
  [json-string]
  (fn [req] {:status 200 :headers {} :body json-string}))

(def test-routes
  {#".*" (json "{}")}) 

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

(facts "about converting keys to keywords"
  (convert-keys {"foo" "bar"}) => {:foo "bar"})

(facts "about simple api requests"
  (with-fake-api
    (api-request "members/me" "") => {}))

