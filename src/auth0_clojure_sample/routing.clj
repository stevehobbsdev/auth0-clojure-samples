(ns auth0-clojure-sample.routing
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [auth0-clojure-sample.views.core :as views]
            [ring.util.response :refer [redirect]]
            [auth0-clojure-sample.auth0 :as auth]))

(def login-redirect-url
  "https://elkdanger.eu.auth0.com/authorize")

(defn handle-login [code]
  (-> (auth/handle-callback code)
      (.getIdToken)))

(defroutes app-routes
  (GET "/" {session :session} (views/index))
  (GET "/profile" [] "<h1>Profile</h1>")
  (GET "/login" [] (redirect (auth/login-url)))
  (GET "/logout" [] "<h1>Loggig out..</h1>")
  (GET "/callback" [code]
    (let [token (handle-login code)]
      (println token)
      (assoc (redirect "/") :cookies {:loggedin true})))
  (route/not-found "<h1>Page not found</h1>"))
