(ns reviews.core
  (:use compojure.core)
  (:require
    [ring.adapter.jetty :as jetty]
    [compojure.route :as route]))

;(defn read-template [template-name]
  ;(slurp (clojure.java.io/resource
    ;(str "templates/" template-name ".mustache"))))

;(defn render-template [template-file params]
   ;(clostache/render (read-template template-file) params))

;(defn index []
	;( render-template "index" {:fahrenheit 15}))

(defroutes main-router
  (GET "/" {param :params} "Hello World!" )
  (route/resources "/") 
  (route/not-found "404 not found") )

(defn -main []
  (jetty/run-jetty
    main-router
    {:port 5000}))
