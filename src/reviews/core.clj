(ns reviews.core
  (:use compojure.core)
  (:use hiccup.core)
  (:use hiccup.form)
  (:require
    [ring.adapter.jetty :as jetty]
    [compojure.route :as route]
    [ring.middleware.params         :only [wrap-params]]))


(def celcius 20)


(defn fahr [value] (
  str ( + (* value 1.8 ) 32 ) ))

(defn input [fahrenheit] (
  html [:form {:action "/result/:celcius" :method "get"} [ :input{ :type "text" :value celcius}]  "°C"  [:input {:type "submit" :value "Calculate"}  ] ]  ) )

(defn result [celcius]
  (html [:p (fahr celcius)] "°F" ))

;[:input {:type "text" :value } ] "°F"

(defn parse-int [s]
  (Integer/parseInt (re-find #"\A-?\d+" s)))

(defroutes main-router
  (GET "/" {param :params} (input [] ) ) 
  ;Celcius null
  (GET "/result/:celcius" [celcius]
        (result (parse-int celcius) )
  )
  (route/resources "/") 
  (route/not-found "404 not found")
  )

(defn -main []
  (jetty/run-jetty
    main-router
    {:port 5000}))
