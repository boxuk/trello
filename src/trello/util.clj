(ns trello.util)

(defn get-env-var [v] (get (System/getenv) v))

(defn- collapse-csv
  "Collapse sequential values to a CSV"
  [[k v]]
  (vector k
    (if (vector? v)
      (clojure.string/join "," v)
      v)))

(defn- generate-params
  "Creates the API parameters part of the query string"
  [params]
  (apply str
    (for [[k v] (map collapse-csv params)]
      (str "&" (name k) "=" v))))

(defn normalize-http-request
  "Given a request that starts with a forward slash, strip the
   slash to normalize the request string"
  [request-string]
  (if (.startsWith request-string "/")
    (subs request-string 1)
    request-string))

(defn get-gravatar-image
  "Given a gravatar hash, return the users avatar.
   If it can't be found a placeholder is returned"
  [hash]
  (let [gravatar-hash hash
        base-url "http://www.gravatar.com/avatar"]
    [:img {:src (format "%s/%s?d=mm" base-url gravatar-hash)}]))

(defn filter-by-param
  "Given a result map, filter out and return only the values for
   the key specified. Utility function for inspecting collections"
  [key, results]
  (when-let [vector? results]
    (doseq [item (map #(get % key) results)]
      (prn item))))
