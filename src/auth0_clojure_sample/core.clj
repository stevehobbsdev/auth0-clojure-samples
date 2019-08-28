(ns auth0-clojure-sample.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.session :refer :all]
            [ring.middleware.reload :refer [wrap-reload]]
            [auth0-clojure-sample.routing :refer :all]))

(defn handler
  (-> app-routes
      (wrap-session)
      (wrap-defaults site-defaults)))
