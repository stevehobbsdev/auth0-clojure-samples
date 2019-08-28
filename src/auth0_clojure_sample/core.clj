(ns auth0-clojure-sample.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.session :refer :all]
            [ring.middleware.reload :refer [wrap-reload]]
            [auth0-clojure-sample.routing :refer :all]))

(def handler
  (-> app-routes
      (wrap-session)
      (wrap-defaults site-defaults)))

(defn -main
  [port-number]
  (jetty/run-jetty handler {:port (Integer. port-number)}))

(defn -dev-main
  [port-number]
  (jetty/run-jetty (wrap-reload #'handler) {:port (Integer. port-number)}))