(ns trello.test.helpers
  (:use clj-http.fake
        trello.client))

(defn- json 
  "Response with specified JSON string"
  [json-string]
  (fn [req] {:status 200 :headers {} :body json-string}))

(def test-routes
  {
    #".*members\/foo.*" (json "{\"name\": \"foo\"}")
    #".*members\/me.*fields=bazzle" (json "{\"bazzle\": 1}")
    #".*members\/me.*" (json "{\"name\": \"tester\"}")
  })

(defmacro with-fake-api [& body]
  `(binding [*auth-key* "" *auth-token* ""]
     (with-fake-routes test-routes
       (do ~@body))))
