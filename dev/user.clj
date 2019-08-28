(ns user
  (:require [auth0-clojure-sample.core :as core]))

(def server (atom nil))

(defn go
  []
  (reset! server (core/-dev-main)))

(defn stop
  []
  (.stop @server))

(defn reset
  []
  (stop)
  (go))

(comment
  (go)
  @server
  (stop)
  (reset))