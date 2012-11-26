(ns stubby.host
    (:gen-class)
    (:use ring.adapter.jetty stubby.core))

(defn -main []
    (run-jetty app {:port 3000}))
