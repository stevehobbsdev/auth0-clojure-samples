(ns auth0-clojure-sample.middleware
  (:require [auth0-clojure-sample.auth0 :as auth]
            [clojure.data.json :as json]))

(defn wrap-user [handler]
  (fn [request]
    (let [
      session (:session request)
      profile-json (:user-profile session)
      profile (when profile-json (json/read-str profile-json :key-fn keyword))]
      (handler (assoc request :user profile)))))