(ns reviews.core
  (:use compojure.core)
  (:use hiccup.core)
  (:require
    [ring.adapter.jetty :as jetty]
    [compojure.route :as route]))


(def celcius 20)


(defn fahr [] (
  str ( + (* celcius 1.8 ) 32 ) ))

(defn index [] (
  html [:form [ [:input{ :type "text" :value celcius}]  "°C"  [:input {:type "text" :value (fahr)} "°F" ] ] ] ) )



(defroutes main-router
  (GET "/" {param :params} (index) ) 
  (route/resources "/") 
  (route/not-found "404 not found") )

(defn -main []
  (jetty/run-jetty
    main-router
    {:port 5000}))
