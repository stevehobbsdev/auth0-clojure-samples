(ns auth0-clojure-sample.routing
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [auth0-clojure-sample.views.core :as views]
            [ring.util.response :refer :all]
            [auth0-clojure-sample.auth0 :as auth]))

(def login-redirect-url
  "https://elkdanger.eu.auth0.com/authorize")

(defroutes app-routes
  (GET "/" {{:keys [auth]} :session} (views/index (when auth (auth :user-info))))
  (GET "/profile" [] "<h1>Profile</h1>")
  (GET "/login" [] (redirect (auth/login-url)))
  (GET "/logout" [] "<h1>Loggig out..</h1>")
  (GET "/callback" [code :as {session :session}]
    (let [data (auth/handle-callback code)
          new-session (assoc session :auth data)]
      (-> (redirect "/")
          (assoc :session new-session))))
  (route/not-found "<h1>Page not found</h1>"))
