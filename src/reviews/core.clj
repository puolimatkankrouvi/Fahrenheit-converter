(ns reviews.core
  (:use compojure.core)
  (:use hiccup.core)
  (:use hiccup.form)
  (:require
    [ring.adapter.jetty :as jetty]
    [compojure.route :as route]))


(def celcius 20)


(defn fahr [] (
  str ( + (* celcius 1.8 ) 32 ) ))

(defn input [] (
  html [:form {:action "/" :method "post"} [ :input{ :type "text" :value celcius}]  "°C"  [:input {:type "text" :value (fahr)} ] "°F" [:input {:type "button" :value "Calculate"}  ] ]  ) )



(defroutes main-router
  (GET "/" {param :params} (input) ) 
  (POST "/" [:as request] (str (request :multipart-params)) )
  (route/resources "/") 
  (route/not-found "404 not found")
  )

(defn -main []
  (jetty/run-jetty
    main-router
    {:port 5000}))
