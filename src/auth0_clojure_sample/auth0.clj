(ns auth0-clojure-sample.auth0
  (:import [com.auth0.client.auth AuthAPI]))

(def config
  {:domain "elkdanger.eu.auth0.com"
   :client-id "nw1AjlUNOiVfKGQpfvQ69q7k9YQhtZ0M"
   :client-secret "OzAFPFyXVBOoge69UBk-q_RNecs_TL5fz11YDlzIX7pBeM065asQRqAlTaDsC8KQ"})

(defn api []
  "Creates the Auth0 API object"
  (AuthAPI. (config :domain) (config :client-id) (config :client-secret)))

(defn handle-callback [code]
  "Exchanges the authorization code for the tokens"
  (-> (api)
      (.exchangeCode code "http://localhost:3000/callback")
      (.setScope "openid profile email")
      (.execute)))

(defn login-url []
  "Builds a URL that the user should redirect to for login"
  (-> (api)
      (.authorizeUrl "http://localhost:3000/callback")
      (.withScope "openid profile email")
      (.build)))