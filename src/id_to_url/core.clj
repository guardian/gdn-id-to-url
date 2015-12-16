(ns id-to-url.core
    (:require
        [id-to-url.data :as data]
        [org.httpkit.client :as http]))

(defn lookup-url [host id]
    (http/get (str "http://" host "/internal-code/content/" id)))

(defn competition-ids [] data/competition-ids)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
