(ns stubby.core
  (:use compojure.core clojure.java.io stubby.resource-parser)
  (:require [compojure.handler :as handler]))

(def base-path (ref ""))

(defn create-response [file-path]
  (parse-resource (slurp file-path)))

(defn create-missing-response [file-path] 
  {:status 404 :body (str "Missing " file-path)})

(defn handle [path params method]
  (let [file-path (str @base-path path "." method)]
    (if (.exists (file file-path)) (create-response file-path) (create-missing-response file-path))))

(defroutes app-routes 
  (GET "*" {path :uri params :params} (handle path params "GET"))
  (PUT "*"  {path :uri params :params} (handle path params "PUT"))
  (POST "*"  {path :uri params :params} (handle path params "POST"))
  (DELETE "*"  {path :uri params :params} (handle path params "DELETE")))

(def app (handler/site app-routes))
