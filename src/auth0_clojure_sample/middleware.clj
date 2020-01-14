(ns auth0-clojure-sample.middleware
  (:require [cheshire.core :refer [parse-string]]))

(defn wrap-user [handler]
  (fn [{session :session :as request}]
    (let [profile-json (:user-profile session)]
      (handler (assoc request :user
                      (when profile-json (parse-string profile-json true)))))))

(defn wrap-host-url [handler]
  (fn [{scheme :scheme host :server-name port :server-port :as request}]
    (let [url (str (name scheme) "://" host (when port (format ":%s" port)))]
      (handler (assoc request :host-url url)))))