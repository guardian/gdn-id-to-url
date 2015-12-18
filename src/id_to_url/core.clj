(ns id-to-url.core
    (:require
        [id-to-url.data :as data]
        [org.httpkit.client :as http]
        [cheshire.core :as json]))

(defn lookup-url [host api-key id]
    {:id id :result (http/get
        (str "http://" host "/internal-code/content/" id)
        {
            :query-params {:api-key api-key}
            :timeout 30000
        })})

(defn extract-url [result]
    (let [
        _ (println result)
        {id :id promise :result} result
        json (json/parse-string (:body @promise) true)]
        (get-in json [:response :content :webUrl])))

(defn generate-urls [host api-key]
    (let [futures (doall (map (fn [id] (lookup-url host api-key id)) data/competition-ids))]
        (map extract-url futures)))

(defn cache-id-1 [cache host api-key id]
    (if (not (contains? @cache id))
        (let [{id :id lookup :result} (lookup-url host api-key id)]
            (if (contains? @lookup :body)
                (let [json (json/parse-string (:body @lookup) true)
                    url (get-in json [:response :content :webUrl])]
                    (swap! cache assoc id url))
                    (recur cache host api-key id)))))

(defn cache-id [cache host api-key id]
    (if (not (contains? @cache id))
        (let [{id :id lookup :result} (lookup-url host api-key id)]
            (if (contains? @lookup :body)
                (let [json (json/parse-string (:body @lookup) true)
                    enhanced-result (assoc @lookup :json json)]
                    (swap! cache assoc id enhanced-result))
                    (recur cache host api-key id)))))

(defn populate-cache [cache host api-key content-type]
    (doall (map (fn [id] (cache-id cache host api-key id)) (content-type data/lookup)))

(defn error-responses [cache]
    (filter (fn [r] (= (get-in r [:json :response :status]) "error")) cache))

(defn ok-responses [cache]
    (filter (fn [r] (= (get-in r [:json :response :status]) "ok")) cache))

(defn find-url [result]
    (get-in result [:json :response :content :webUrl]))

;; (->> (vals @cache) (filter ok-responses) (map find-url))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
