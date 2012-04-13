(ns trello.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.string :as string]))

;;; Clojure wrapper for the Trello.com API

(def base-url "https://api.trello.com/1/")

(defn get-env-var [v]
  "Gets an env var. Used in development to store auth data"
  (get (System/getenv) v))

(def ^:dynamic auth-key (get-env-var "TRELLO_KEY"))

(def ^:dynamic auth-token (get-env-var "TRELLO_TOKEN"))

(defn normalize-request
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
                (string/join "," v)
                v)))

(defn- generate-params
  "Creates the API parameters part of the query string"
  [params]
  (apply str 
    (for [[k v] (map collapse-csv params)] 
      (str "&" (name k) "=" v))))

(defn generate-url
  [request k t & [params]]
  (str base-url request
       (format "?key=%s&token=%s" k t)
       (generate-params params)))

(defn- make-api-request [http_method, query, auth & params]
  "Make a request to the Trello API and parse
   the response. If the response fails catch and return the HTTP error.
   auth is a vector [trello_key trello_token]"
  (let [[k t] auth
        url (generate-url query k t)
        req {:url url :method http_method}
        body (get (client/request req) :body)] 
        (json/parse-string body true)))

(defn api-request [method q]
  "Make a request to the API. Returns JSON response or HTTP error code"
  (if (or (nil? auth-key) (nil? auth-token))
    (prn "Please set your auth key and token before making a request")
    (try
      (make-api-request method q [auth-key auth-token])
    (catch Exception e e))))

;;; General Requests 

(defn member
  "Returns all the information about the specified user,
   or the current user if none specified"
  ([] (member "me"))
  ([id] (api-request :get (str "members/" id))))

(defn get-all-query
  "Perform a query that returns all results for a given search term q"
  [q]
  (api-request :get (format "members/my/%s/all" q)))
  
(defn get-all [item]
  "Return all results for item. Item can be either a string or keyword"
  (let [query (if-not (keyword? item) (keyword item) item)]
  (condp = query
    :boards (get-all-query "boards")
    :cards (get-all-query "cards")
    :organizations (get-all-query "organizations")
    (prn (format "Not such item %s" item)))))

(defn filter-by-param
  "Given a result map, filter out the key specified.
   Utility function for inspecting collections"
  [key, results]
  (when-let [vector? results]
    (doseq [item (map #(get % key) results)]
      (prn item))))
  
;;; Boards

(defn get-board
  "Get a single Trello board"
  ([] (get-all :boards))
  ([id] (api-request :get (format "boards/%s" id))))

(defn -main
  [& args]
  ())
