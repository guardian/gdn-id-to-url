(ns id-to-url.core
    (:require
        [id-to-url.data :as data]
        [org.httpkit.client :as http]
        [cheshire.core :as json]))

(defn lookup-url [host id]
    {:id id :result (http/get (str "http://" host "/internal-code/content/" id))})

(defn extract-url [result]
    (let [
        {id :id promise :result} result
        json (json/parse-string (:body @promise) true)]
        (get-in json [:response :content :webUrl])))

(defn competition-ids [] data/competition-ids)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
