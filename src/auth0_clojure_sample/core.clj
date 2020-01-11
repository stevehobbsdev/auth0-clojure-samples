(ns auth0-clojure-sample.core
  (:gen-class)
  (:require
   [ring.middleware.defaults :refer [site-defaults, wrap-defaults]]
   [ring.middleware.session :refer [wrap-session]]
   [auth0-clojure-sample.routing :refer [app-routes]]
   [auth0-clojure-sample.middleware :refer [wrap-user wrap-host-url]]))

(def app-defaults
  (-> site-defaults
      (assoc-in [:session :cookie-attrs :same-site] :lax)))

(def app
  (-> app-routes
      wrap-user
      wrap-host-url
      (wrap-defaults app-defaults)
      ))
