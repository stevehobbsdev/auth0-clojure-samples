(ns auth0-clojure-sample.routing
  (:require [compojure.core :refer [defroutes, GET]]
            [compojure.route :as route]
            [auth0-clojure-sample.views.index :as index]
            [ring.util.response :refer [redirect]]
            [auth0-clojure-sample.auth0 :as auth]))

(def login-redirect-url
  "https://elkdanger.eu.auth0.com/authorize")

(defn handle-login [code]
  (-> (auth/handle-callback code)
      (.getIdToken)))

(defn wrap-auth [session fn]
  (let [profile (when (:token session) (auth/get-user-profile (:token session)))]
    (println profile)
    (fn profile)))

(defroutes app-routes
  (GET "/" {session :session} (wrap-auth session index/page))
  (GET "/profile" [] "<h1>Profile</h1>")
  (GET "/login" [] (redirect (auth/login-url)))
  (GET "/logout" [] "<h1>Logging out..</h1>")
  (GET "/callback" [code :as {session :session}]
    (let [token (handle-login code)
          session (assoc session :token token)]
      ; (println (auth/get-user-profile token))
      (assoc (redirect "/") :cookies {:loggedin true} :session session)))
  (route/not-found "<h1>Page not found</h1>"))
