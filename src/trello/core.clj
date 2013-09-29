(ns ^{:doc "The client namespace contains the raw HTTP functions for accessing
            and parsing the responses from the Trello API."}
  trello.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.string :as string]))

;; Basic stuff
;; **********************************************************

(def +base-url+ (atom "https://api.trello.com/1"))

(def +authorize-url+ (atom "https://trello.com/1/authorize"))

(defn full-url [endpoint]
  (str @+base-url+ endpoint))

(defn settings [f]
  (binding [*read-eval* false]
    (with-open [r (clojure.java.io/reader f)]
      (read (java.io.PushbackReader. r)))))

(defn string->json [^String json-string]
  (json/parse-string json-string true))

;; HTTP requests
;; **********************************************************

(defn request-builder
  "Generic request builder that deals with JSON or HTML requests"
  [auth method url params]
  (let [auth-params {:key (:key auth) :token (:token auth)}
        query-params (merge auth-params params)]
    { :method method
      :query-params query-params
      :url url } ))

(defn request
  "All API requests pass through this function"
  [auth method url & params]
  (let [url-full (full-url url)]
    (when (every? true? (map (partial contains? auth) [:key :token]))
      (let [builder (request-builder auth method url-full (or (first params) {}))]
        (try
          (->> (client/request builder)
               :body
               string->json)
        (catch Exception e
          (json/generate-string
            {:error (.toString e)}
            {:escape-non-ascii true})))))))

(def select-values (comp vals select-keys))

(defn auth-map-from-settings []
  (let [config (into (sorted-map) (settings "config.clj"))]
    (when-let [[token key] (select-values config [:key :token])]
      {:key key :token token})))

;; ************************************************************
;; BOARDS
;; ************************************************************

(comment
  (def auth (auth-map-from-settings))
    (board-all auth))

(defn board-all [auth]
  (request auth :get "/members/me/boards"))

(defn active-boards 
  "Returns only active Trello boards"
  [auth]
  (->> (board-all auth)
       (filter #(= (:closed %) false))))

(def active-board-names 
  "Returns the names of all active Trello boards"
  (comp (partial map :name) active-boards))
 
(defn board-get [auth id]
  (request auth :get (format "/boards/%s" id)))

(defn board-actions [auth id]
  (request auth :get (format "/boards/%s/actions" id)))

(defn board-cards [auth id]
  (request auth :get (format "/boards/%s/cards" id)))

(defn board-checklists [auth id]
  (request auth :get (format "/boards/%s/checklists" id)))

(defn board-lists [auth id]
  (request auth :get (format "/boards/%s/lists" id)))

(defn board-members [auth id]
  (request auth :get (format "/boards/%s/members" id)))

(defn board-memberships [auth id]
  (request auth :get (format "/boards/%s/memberships" id)))

(defn board-organization [auth id]
  (request auth :get (format "/boards/%s/organization" id)))

(defn board-create
  "Create a new Trello board. Name is required"
  [auth attributes]
  (when (contains? attributes :name)
    (request auth :post "/boards" attributes)))

(comment
  (board-create {:key "" :token ""} {:name "new trello board"}))

;; ************************************************************
;; Cards
;; ************************************************************

(defn get-card [auth id]
  (request auth :get (format "/cards/%s" id)))

;; ************************************************************
;; Members
;; ************************************************************

(defn me
  "Return the Trello profile for the current user"
  [auth]
  (request auth :get "/members/me"))

;; ************************************************************
;; Command line client
;; ************************************************************

(defn -main [& args]
  (println "Running client"))

