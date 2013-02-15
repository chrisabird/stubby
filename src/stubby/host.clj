(ns stubby.host
    (:gen-class)
    (:use 
      ring.adapter.jetty 
      stubby.core 
      [clojure.tools.cli :only [cli]]))
(defn -main [& args]
  (println "Stubby 1.1")
  (let [[options args banner] (cli args
    ["-p" "--port" "Listen on this port" :parse-fn #(Integer. %) :default 3000] 
    ["-d" "--data-directory" "Directory for request fixtures"])]
    (dosync (ref-set base-path (:data-directory options)))
    (if (and (:port options) (:data-directory options))
        (run-jetty app {:port (:port options)})
      (println banner))))
