(ns ^{:doc "The client namespace contains the raw HTTP functions for accessing
            and parsing the responses from the Trello API."}
  trello.client
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.string :as string]))

(def ^{:doc "Define the main URL for the Trello API in one place"}
  base-url "https://api.trello.com/1/")

(defn get-env-var
  "Gets an environment variable. Configuration is stored in environment
   variables and this allows easy access to that."
  [v]
  (get (System/getenv) v))

(def ^{:dynamic true :doc "Your key for the Trello API"}
  auth-key (get-env-var "TRELLO_KEY"))

(def ^{:dynamic true :doc "Your token for the Trello API"}
  auth-token (get-env-var "TRELLO_TOKEN"))

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
  (vector k (if (vector? v) 
                (string/join "," v) v)))

(defn- generate-params
  "Creates the API parameters part of the query string"
  [params]
  (apply str 
    (for [[k v] (map collapse-csv params)] 
      (str "&" (name k) "=" v))))

(defn- generate-url
  "Creates an absolute API URL with authentication tokens, and extra
  parameters for each endpoint"
  [request k t & [params]]
  (str base-url request
       (format "?key=%s&token=%s" k t)
       (generate-params params)))

(defn- make-api-request 
  "Make a request to the Trello API and parse
   the response. If the response fails catch and return the HTTP error.
   auth is a vector [trello_key trello_token]"
  [http_method query auth & [params]]
  (let [[k t] auth
        url (generate-url query k t params)
        req {:url url :method http_method}
        body (get (client/request req) :body)] 
        (json/parse-string body true)))

;; Public

(defn api-request 
  "Make a request to the API. Returns JSON response or HTTP error code."
  [method q & [params]]
  (if (or (nil? auth-key) (nil? auth-token))
    (prn "Please set your auth key and token before making a request")
    (try
      (make-api-request method q [auth-key auth-token] params)
    (catch Exception e
      (if (boolean (re-find #"404" (.getMessage e)))
        (prn (format "404. Could not find %s" q))
        (throw e))))))
