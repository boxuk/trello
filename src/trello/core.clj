(ns ^{:doc "The client namespace contains the raw HTTP functions for accessing
            and parsing the responses from the Trello API."}
  trello.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.string :as string]))

;; Basic stuff
;; **********************************************************

(defonce +base-url+ (atom "https://api.trello.com/1/"))

(defonce +authorize-url+ (atom "https://trello.com/1/authorize"))

(defn full-url [endpoint]
  (let [parts [@+base-url+ endpoint]]
    (apply str parts)))

(defn settings 
  "Extract auth settings from a config.clj file"
  [f]
  (binding [*read-eval* false]
    (with-open [r (clojure.java.io/reader f)]
      (read (java.io.PushbackReader. r)))))

(defn string->json 
  "Parse a string to JSON"
  [json-string]  
  (json/parse-string json-string true))

;; HTTP requests
;; **********************************************************

(defn request-builder 
  "Builds a request to be executed by a HTTP client"
  [auth method url & params]
  (let [query-params (merge auth (into {} params))]
    (assert (keyword? method))
    {:method method
     :as :json
     :query-params query-params
     :url url}))

(defmacro with-exception-handling 
  "A helper macro for performing request with a try catch"
  [msg & forms]
  `(try (do ~@forms)
     (catch Exception ex# 
       {:error ~msg})))

(defn ->>request
  "All API requests pass through this function"
  [auth method url & params]
  (let [url-full (full-url url)
        builder (request-builder auth method url-full (into {} params))]
   (with-exception-handling "Bad request"
     (->> (client/request builder) :body))))

(def memo-request (memoize ->>request))

(def select-values (comp vals select-keys))

(defn auth-map-from-settings
  "Reads in auth info from config.clj"
  []
  (let [config (into (sorted-map) (settings "config.clj"))]
    (when-let [[token key] (select-values config [:key :token])]
      {:key key :token token})))

(defn format-auth 
  "Helper for fns where a user wants to explicitly pass a key and token
   rather than using the auth map"
  [key oauth-token]
  {:key key :token oauth-token})

;; ************************************************************
;; BOARDS
;; ************************************************************

(defn boards 
  "Fetches all Trello boards for a user"
  ([auth]
     (->>request auth :get "members/me/boards"))
  ([key oauth-token]
    (boards (format-auth key oauth-token))))

(def m-boards (memoize-boards))

(comment
  (boards "YOURKEY" "YOURTOKEN"))

(defn board
  "Fetch a single Trello board"
  ([auth board-identifier]
    (->>request auth :get (format "boards/%s" board-identifier)))
  ([key oauth-token board-identifier]
   (board (format-auth key oauth-token) board-identifier)))

(def m-board (memoize board))

(defn active-boards 
  "Returns only active Trello boards"
  [auth]
  (->> (boards auth)
       (filter #(= (:closed %) false))))

(def active-board-names 
  "Returns the names of all active Trello boards"
  (comp (partial map :name) active-boards))
 
(defn board-actions [auth id]
  (->>request auth :get (format "boards/%s/actions" id)))

(defn board-cards [auth id]
  (->>request auth :get (format "boards/%s/cards" id)))

(defn board-checklists [auth id]
  (->>request auth :get (format "boards/%s/checklists" id)))

(defn board-lists [auth id]
  (->>request auth :get (format "boards/%s/lists" id)))

(defn board-members [auth id]
  (->>request auth :get (format "boards/%s/members" id)))

(defn board-memberships [auth id]
  (->>request auth :get (format "boards/%s/memberships" id)))

(defn board-organization [auth id]
  (->>request auth :get (format "boards/%s/organization" id)))

(defn board-create
  "Create a new Trello board. Name is required"
  [auth attributes]
  (when (contains? attributes :name)
    (->>request auth :post "boards" attributes)))

(comment
  (board-create {:key "" :token ""} {:name "new trello board"}))

;; ************************************************************
;; Cards
;; ************************************************************

(defn get-card [auth id]
  (->>request auth :get (format "cards/%s" id)))

;; ************************************************************
;; Members
;; ************************************************************

(defn me
  "Return the Trello profile for the current user"
  [auth]
  (->>request auth :get "members/me"))

;; ************************************************************
;; Command line client
;; ************************************************************

(defn -main 
  [& args]
  (println "Running client"))

