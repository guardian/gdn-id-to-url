(defproject id-to-url "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-opts ["-Xms512m" "-Xmx2G" "-XX:MaxPermSize=512m" "-server"]
  :dependencies [
  [org.clojure/clojure "1.7.0"]
  [cheshire "5.5.0"]
  [http-kit "2.1.19"]])
