(ns auth0-clojure-sample.routing
  (:require [compojure.core :refer [defroutes, GET]]
            [compojure.route :as route]
            [auth0-clojure-sample.views.index :as index]
            [ring.util.response :refer [redirect]]
            [auth0-clojure-sample.auth0 :as auth]
            [clojure.data.json :as json]
            ))

(def login-redirect-url
  "https://elkdanger.eu.auth0.com/authorize")

(defn handle-login [code]
  (-> (auth/handle-callback code)
      (.getIdToken)))

(defroutes app-routes
  (GET "/" {user :user} (index/html user))
  (GET "/profile" [] "<h1>Profile</h1>")
  (GET "/login" [] (redirect (auth/login-url)))
  (GET "/logout" [] "<h1>Logging out..</h1>")
  (GET "/callback" [code :as {session :session}]
    (let [session 
          (->> (handle-login code)
              auth/get-user-profile
              json/write-str
              (assoc session :user-profile))]
      (assoc (redirect "/") :session session)))
  (route/not-found "<h1>Page not found</h1>"))
