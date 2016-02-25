(defproject id-to-url "0.1.0-SNAPSHOT"
  :description "Helps relate R2 ids to site urls"
  :license {:name "Apache 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :jvm-opts ["-Xms512m" "-Xmx2G" "-XX:MaxPermSize=512m" "-server"]
  :dependencies [
  [org.clojure/clojure "1.7.0"]
  [cheshire "5.5.0"]
  [http-kit "2.1.19"]])
