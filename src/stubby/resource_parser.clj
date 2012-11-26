(ns stubby.resource-parser
  (:use cheshire.core)) 

(defn parse-resource [x] 
  (let [matcher (re-matcher #"(?sm)^---\s*\n(.*?\n?)^---\s*$\n?(.*)" x)
        matches (re-find matcher)]
    (if (not (nil? matches)) 
      (assoc (parse-string (second matches) true) :body (nth matches 2))
      {:body x})))
