(ns stubby.core
  (:use compojure.core clojure.java.io stubby.resource-parser)
  (:require [compojure.handler :as handler]))

(def base-path (ref ""))

(defn create-response [query file-path]
  (find-response query (slurp file-path)))

(defn create-missing-response [file-path] 
  {:status 404 :body (str "Missing " file-path)})

(defn handle [path query method]
  (let [file-path (str @base-path path "." method)]
    (if (.exists (file file-path)) (create-response query file-path) (create-missing-response file-path))))

(defroutes app-routes 
  (GET "*" {path :uri query :query-string} (handle path query "GET"))
  (PUT "*"  {path :uri query :query-string} (handle path query "PUT"))
  (POST "*"  {path :uri query :query-string} (handle path query "POST"))
  (DELETE "*"  {path :uri query :query-string} (handle path query "DELETE")))

(def app (handler/site app-routes))
