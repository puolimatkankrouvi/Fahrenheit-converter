(ns reviews.core
  (:use compojure.core)
  (:use hiccup.core)
  (:use hiccup.form)
  (:use hiccup.page)
  (:use ring.middleware.params)
  (:require
    [ring.adapter.jetty :as jetty]
    [compojure.route :as route]
    [ring.middleware.params         :only [wrap-params]]))


(def celcius 20)




(defn fahr [value] (
  str ( + (* value 1.8 ) 32 ) ))

;Main page
(defn input [fahrenheit] (
  html [:head [:title  "Celcius to Fahrenheit Converter" ] (include-css "/tyyli.css" ) ] [:body [:h1 "Celcius to Fahrenheit Converter" ] [:form {:action "/result/" :method "get"} [:div [ :input{ :class "input_field" :type "text" :value celcius}]  [:p "°C"] ] [:input {:type "submit" :value "Convert" :class "button"} ] ] ]  ) )

;Page that gives the result
(defn result [celcius]
  (html [:p (fahr celcius)] "°F" ))

;[:input {:type "text" :value } ] "°F"

(defn parse-int [s]
  (Integer/parseInt (re-find #"\A-?\d+" s)))

(defroutes main-router
  (GET "/" {param :params} (input [] ) ) 
  ;Celcius null
  (GET "/result/" {params :params}
        (result (parse-int (get params "celcius")))
  )
  (route/resources "/") 
  (route/not-found "404 not found")
  )

(def app
  (wrap-params main-router))

(defn -main []
  (jetty/run-jetty
    app
    {:port 5000}))
