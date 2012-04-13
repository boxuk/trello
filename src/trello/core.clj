(ns trello.core
  (:require [clj-http.client :as client])
  (:use clojure.walk
        [cheshire.core :as json]))

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

(defn generate-query-string
  "Abstracting this function to make it easier to debug
  the request being passed to the API"
  [request k t & params]
  (format "%s%s?key=%s&token=%s%s"
    base-url request k t
    (apply str 
      (for [[k v] (first params)] 
        (str "&" (name k) "=" v)))))

(defn convert-keys
  "Convert string keys into keywords. Abstracted into
   separate function to make it easier to test.
  @todo remove?"
  [m]
  (keywordize-keys m))

(defn- make-api-request [http_method, query, auth & params]
  "Make a request to the Trello API and parse
   the response. If the response fails catch and return the HTTP error.
   auth is a vector [trello_key trello_token]"
  (let [[k t] auth
        url (generate-query-string query k t)
        req {:url url :method http_method}] 
        (json/parse-string
          (get (client/request req) :body))))

(defn api-request [method q]
  "Make a request to the API. Returns JSON response or HTTP error code"
  (if (or (nil? auth-key) (nil? auth-token))
    (prn "Please set your auth key and token before making a request")
    (try
      (make-api-request method q [auth-key auth-token])
    (catch Exception e e))))

;;; General Requests 

(defn member
  "Returns all the information about the current user"
  []
  (api-request :get "members/me"))

(defn all-boards
  "Return all the boards for the current user"
  []
  (api-request :get "members/my/boards/all"))

(defn all-organizations
  "Return all the organizations this member belongs to"
  []
  (api-request :get "members/my/organizations"))

(defn filter-by-param
  "Given a result map, filter out the key specified.
   Utility function for inspecting collections"
  [key, results]
  (let [collection (if (map? results)
    (vector (get results key))
    (map #(get % key) results))]
    (doseq [item collection]
      (prn item))))
  
;;; Board API

(defn -main
  [& args]
  ())
