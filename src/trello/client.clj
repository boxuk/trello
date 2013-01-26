(ns ^{:doc "The client namespace contains the raw HTTP functions for accessing
            and parsing the responses from the Trello API."}
  trello.client
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.string :as string]))

(def base-url "api.trello.com/1/")

(def authorize-url "https://trello.com/1/authorize")

(def settings 
  (binding [*read-eval* false]
    (with-open [r (clojure.java.io/reader "config.clj")]
      (read (java.io.PushbackReader. r)))))

(defn get-env-var [v] 
  (get (System/getenv) v))

(def ^:dynamic *auth-key* (get-env-var "TRELLO_KEY"))

(def ^:dynamic *auth-token* (get-env-var "TRELLO_TOKEN"))

(def ^:dynamic *protocol* (atom "http"))

(defmacro with-https
  {:doc "Make a HTTP request using https"}
  [& body]
  `(binding [*protocol* (atom "https")]
     (do ~@body)))

(defn- normalize-request
  "Given a request that starts with a forward slash, strip the
   slash to normalize the request string"
  [request-string]
  (if (.startsWith request-string "/")
    (subs request-string 1)
    request-string))

(defn- collapse-csv
  "Collapse sequential values to a CSV"
  [[k v]]
  (vector k 
    (if (vector? v) 
      (string/join "," v) 
      v)))

(defn- generate-params
  "Creates the API parameters part of the query string"
  [params]
  (apply str 
    (for [[k v] (map collapse-csv params)] 
      (str "&" (name k) "=" v))))

(defn- generate-url
  "Creates an absolute API URL with authentication tokens, and extra
  parameters for each endpoint"
  [request key token & [params]]
  (with-https
    (str @*protocol* "://" base-url request
       (format "?key=%s&token=%s" key token)
       (generate-params params))))

(defn make-api-request 
  "Make a request to the Trello API and return a response map"
  [method query key token & [params]]
  (let [url (generate-url query key token params)
        req {:url url :method method}]
    (json/parse-string 
      (get (client/request req) :body)
        true)))

(defn api-request 
  [method q & params]
  (if (and (nil? *auth-key*) (nil? *auth-token*))
    (print "Please set your auth key and token before making a request")
    (try
      (make-api-request method q *auth-key* *auth-token* params)
    (catch Exception e
      (if (boolean (re-find #"404" (.getMessage e)))
        (prn (format "404. Could not find %s" q))
        (throw e))))))

(defmacro with-auth [k token & body]
  `(binding [*auth-key* ~k
            *auth-token* ~token]
     (do ~@body)))

(defn auth [settings] 
  ((juxt :key :token) settings))
    
