(ns trello.util)

(defn get-gravatar-image
  "Given a gravatar hash, return the users avatar. If it can't be found a placeholder is returned"
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
