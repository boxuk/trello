(ns trello.core
  (:require [clj-http.client :as client])
  (:use [cheshire.core :as json]))

(def base-url "https://api.trello.com/1/")

(defn get-env-var [v]
  "Gets an env var. Used in development to store auth data"
  (get (System/getenv) v))

(def auth_key (get-env-var "TRELLO_KEY"))

(def auth_token (get-env-var "TRELLO_TOKEN"))

(defn- make-api-request [http_method, query, auth]
  "Make a request to the Trello API and parse
   the response. If the response fails catch and return the HTTP error.
   auth is a vector [trello_key trello_token]"
  (let [[k t] auth
        url (str base-url query "?key=" k "&token=" t)
        req {:url url :method http_method}]
    (into {} 
      (for [[k v] 
        (json/parse-string
          (get (client/request req) :body))]
    [(keyword k) v]))))
	  
(defn api-request [method q]
  "Make a request to the API. Returns JSON response or HTTP error code"
  (make-api-request method "members/me" [auth_key auth_token]))

;; General Requests 

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

(defn -main
  [& args]
  ())
