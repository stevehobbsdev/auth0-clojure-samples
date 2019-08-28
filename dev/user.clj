(ns user
  (:require [auth0-clojure-sample.core :as core]))

(def server (atom nil))

(defn go
  []
  (reset! server (core/-main 3000)))

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