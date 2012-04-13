(ns trello.util)

(defn filter-by-param
  "Given a result map, filter out and return only the values for
   the key specified. Utility function for inspecting collections"
  [key, results]
  (when-let [vector? results]
    (doseq [item (map #(get % key) results)]
      (prn item))))

