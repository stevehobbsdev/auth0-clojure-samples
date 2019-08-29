(ns auth0-clojure-sample.routing
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [auth0-clojure-sample.views.core :as views]
            [ring.util.response :refer :all]
            [auth0-clojure-sample.auth0 :as auth]))

(def login-redirect-url
  "https://elkdanger.eu.auth0.com/authorize")

(defn handle-login [code]
  (-> (auth/handle-callback code)
      (.getIdToken)))

(defroutes app-routes
  (GET "/" {{:keys [logged-in]} :session} (views/index logged-in))
  (GET "/profile" [] "<h1>Profile</h1>")
  (GET "/login" [] (redirect (auth/login-url)))
  (GET "/logout" [] "<h1>Loggig out..</h1>")
  (GET "/callback" [code :as {session :session}]
    (let [token (handle-login code)
          session (assoc session :logged-in true)]
      (-> (redirect "/")
          (assoc :session session))))
  (route/not-found "<h1>Page not found</h1>"))
