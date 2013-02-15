(ns stubby.resource-parser
  (:use cheshire.core)) 

(defn build-response[[header body]]
  {:headers (parse-string header true) :body body})

(defn extract-responses [sections]
  (map #(build-response %) (partition 2 2 [] sections)))

(defn parse-responses [x]
  (let [sections (clojure.string/split x #"(?m)^---")]
    (if (= 1 (count sections))
      [{:headers {} :body x}]
      (extract-responses (drop 1 sections)))))

(defn find-response [q x]
  (let [r (parse-responses x)]
    (first (filter #(= q (:querystring (:headers %))) r))))
