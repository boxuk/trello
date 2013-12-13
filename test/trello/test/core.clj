(ns trello.test.core
  (:use trello.core
        midje.sweet))

(fact "full-url returns a full and correct url"
  (full-url "boards") => "https://api.trello.com/1/boards")

(facts "a request is built and returned as a clj map"
  (let [request (request-builder {:key "A"} :get "boards" {:foo :foo})]
    (:method request) => :get
    (:query-params request) => {:key "A" :foo :foo}))


