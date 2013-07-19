(ns ^{:doc "The client namespace contains the raw HTTP functions for accessing
            and parsing the responses from the Trello API."}
  trello.client
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
      (let [builder (request-builder auth method url-full params)]
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

;; End HTTP utils
;; ************************************************************

