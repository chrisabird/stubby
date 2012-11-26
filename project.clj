(defproject stubby "0.1.0-SNAPSHOT"
  :description "Stubbed REST Endpoint"
  :dependencies [ 
    [org.clojure/clojure "1.4.0"]
    [ring/ring-jetty-adapter "1.1.1"]
    [compojure "1.1.1"]]
  :profiles { 
    :dev {
      :dependencies [
        [ring-mock "0.1.2"]
        [midje "1.4.0"]
        [clj-http "0.5.7"]]
      :plugins [
        [lein-ring "0.7.5"]
        [lein-midje "2.0.0-SNAPSHOT"]]}}
  :ring {:handler stubby.core/app}
  :checksum :pass
  :main stubby.host
  :url "http://github.com/chrisabird/stubby"
  :license {:name "MIT" :url "http://mit-license.org/"})
