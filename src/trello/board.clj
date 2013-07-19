(ns trello.board
  (refer-clojure :exclude [get])
  (:use [trello.client :as client]))

(comment
  (def auth (client/auth-map-from-settings))
  (all auth))

(defn all [auth]
  (client/request auth :get "/members/me/boards"))

(defn get [auth id]
  (client/request auth :get (format "/boards/%s" id)))


;; (defn all [] (client/request :get (full-url (:all board-urls))))

;; (defn get
;;   "Get Trello boards
;;     - (get) All boards
;;     - (get 123) Board by id
;;     - (get 123 :resource) All resources for board
;;       Resource options [:actions :cards :checklists :lists :membersInvited :members]
;;   "
;;   ([] (all)) ;; all
;;   ([id] (client/api-request :get (format "boards/%s" id)))
;;   ([id resource]
;;     (client/api-request :get
;;       (format "boards/%s/%s" id
;;         (name resource)))))

;; (defn create
;;   "Create a new Trello board
;;    - name (required)
;;      Valid Values: A string with a length from 1 to 16384
;;    - desc (optional)
;;      Valid Values: A user ID or name
;;    - idOrganization (optional)
;;      Valid Values: The id or name of the organization to add the board to.
;;    - idBoardSource (optional)
;;      Valid Values: The id of the board to copy into the new board"
;;   ([name] (client/api-request :post "boards" {:name name}))
;;   ([name other-params]
;;    (let [params (merge {:name name} other-params)]
;;      (client/api-request :post
;;        "boards" {:name name}))))

;; (defn close
;;   "Toggle a board between closed and open
;;    - value
;;      Valid Values: true or false
;;   "
;;   [id value]
;;   (client/api-request :put
;;     (format "boards/%s/closed" id) {:value value}))

;; (defn get-all-list-names-for-board
;;   "Return a sequence of list names for a board"
;;   [board-id]
;;   (let [lists (get board-id :lists)]
;;     (map
;;       (fn [list]
;;         (:name list))
;;       lists)))

