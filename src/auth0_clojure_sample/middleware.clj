(ns auth0-clojure-sample.middleware
  (:require [auth0-clojure-sample.auth0 :as auth]
            [clojure.data.json :as json]))

(defn wrap-user [handler]
  (fn [{session :session :as request}]
    (let [profile-json (:user-profile session)]
      (handler (assoc request :user
        (when profile-json (json/read-str profile-json :key-fn keyword)))))))

(defn wrap-host-url [handler]
  (fn [{scheme :scheme host :server-name port :server-port :as request}]
    (let [url (str (name scheme) "://" host (when port (format ":%s" port)))]
      (handler (assoc request :host-url url)))))