(ns auth0-clojure-sample.routing
  (:require [compojure.core :refer [defroutes, GET]]
            [compojure.route :as route]
            [auth0-clojure-sample.views.index :as index]
            [auth0-clojure-sample.views.profile :as profile]
            [ring.util.response :refer [redirect]]
            [auth0-clojure-sample.auth0 :as auth]
            [cheshire.core :refer [generate-string]]))

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
  (GET "/profile" {user :user} (profile/html user))
  (GET "/login" [] (redirect (auth/login-url)))
  (GET "/logout" {host-url :host-url}
    (-> (redirect (logout-url host-url))
        (assoc :session nil)))
  (GET "/callback" [code :as {session :session}]
    (->> (handle-login code)
         auth/get-user-profile
         generate-string
         (assoc session :user-profile)
         (assoc (redirect "/") :session)))
  (route/not-found "<h1>Page not found</h1>"))
