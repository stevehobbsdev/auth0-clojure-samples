(ns auth0-clojure-sample.routing
  (:require [compojure.core :refer [defroutes, GET]]
            [compojure.route :as route]
            [auth0-clojure-sample.views.index :as index]
            [ring.util.response :refer [redirect]]
            [auth0-clojure-sample.auth0 :as auth]
            [clojure.data.json :as json]
            ))

(def login-redirect-url
  (format "https://%s/authorize" (:domain auth/config)))

(defn logout-url [return-to]
  (format "https://%s/v2/logout?returnTo=%s&client_id=%s"
    (:domain auth/config) return-to (:client-id auth/config)))

(defn handle-login [code]
  (-> (auth/handle-callback code)
      (.getIdToken)))

(defroutes app-routes
  (GET "/" {user :user} (index/html user))
  (GET "/profile" [] "<h1>Profile</h1>")
  (GET "/login" [] (redirect (auth/login-url)))
  (GET "/logout" {host-url :host-url}
    (-> (redirect (logout-url host-url))
        (assoc :session nil)))
  (GET "/callback" [code :as {session :session}]
    (->> (handle-login code)
        auth/get-user-profile
        json/write-str
        (assoc session :user-profile)
        (assoc (redirect "/") :session)))
  (route/not-found "<h1>Page not found</h1>"))
