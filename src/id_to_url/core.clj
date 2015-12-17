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

(defn cache-id [cache host api-key id]
    (if (not (contains? @cache id))
        (let [{id :id lookup :result} (lookup-url host api-key id)]
            (if (contains? @lookup :body)
                (let [json (json/parse-string (:body @lookup) true)
                    url (get-in json [:response :content :webUrl])]
                    (swap! cache assoc id url))
                    (recur cache host api-key id)))))

(defn populate-cache [cache host api-key]
    (doall (map (fn [id] (cache-id cache host api-key id)) data/competition-ids)))

(defn competition-ids [] data/competition-ids)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
