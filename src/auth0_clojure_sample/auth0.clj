(ns auth0-clojure-sample.auth0
  (:import [com.auth0.client.auth AuthAPI]
           [com.auth0.jwt JWT]))

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

(defn- map-claim [claim]
  (or (.asString claim) (.asInt claim)))

(defn- to-map [hash]
  (into {} (for [[k v] hash] [(keyword k) (map-claim v)])))

(defn get-user-profile [id-token]
  (-> (JWT.)
      (.decodeJwt id-token)
      (.getClaims)
      (to-map)))

(comment
  (let [token "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik1VWTVORVl6TXpNM05USXpNVGxFUmtGRVJFRXhRMEZFUVRVNU5UazBRekpFUlRWR1JUazJPQSJ9.eyJuaWNrbmFtZSI6InN0ZXZlaG9iYnNkZXYiLCJuYW1lIjoiU3RldmUgSG9iYnMiLCJwaWN0dXJlIjoiaHR0cHM6Ly9hdmF0YXJzMC5naXRodWJ1c2VyY29udGVudC5jb20vdS83NjY0MDM_dj00IiwidXBkYXRlZF9hdCI6IjIwMTktMDktMTBUMjA6NTk6NDAuNTYwWiIsImVtYWlsIjoic3RldmUuaG9iYnMubWFpbEBnbWFpbC5jb20iLCJpc3MiOiJodHRwczovL2Vsa2Rhbmdlci5ldS5hdXRoMC5jb20vIiwic3ViIjoiZ2l0aHVifDc2NjQwMyIsImF1ZCI6Im53MUFqbFVOT2lWZktHUXBmdlE2OXE3azlZUWh0WjBNIiwiaWF0IjoxNTY4MTQ5MTgwLCJleHAiOjE1NjgxODUxODB9.qgRDoF3UN8ZnRneJBFvly_qppz-R77ojO3S_-wwmx31PkeDvI__wyG8eIf9KvRvOTq24s_n1hJJPKzVnoNs-0ehr6FCr_v51c8cVOA0wCQjGUoveBxkOnXvaBbyyaFraTu8Pp8P5pWL1nQ93kCzlfyTYj81IExf0r012C6obb_spl7lK-d-hAn_y4Y_Y8eLCiFfFyadbGMQk3IpFq6KHsUCDkqAVC2QtxNo51jNWK6E8yczcaFFFA7GJJfQ-eNJrO_Pda5IWSwFU1l0KjXUs4S8DISI0AN3US_-wiQSlIl5I0jz0mWVaOX2GouPqGyW0ojpRF8FFmcFAEXNNuwsY-Q"]
    (:email (get-user-profile token))))
