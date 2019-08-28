(ns auth0-clojure-sample.views.core
  (:require [auth0-clojure-sample.views.layouts :as layouts]
            [hiccup.element :refer [link-to]]))

(def index-hero
  [:div {:class "text-center hero my-5"}
   [:img {:class "mb-3 app-logo" :src "/logo.png" :alt "Clojure logo"}]
   [:h1 {:class "mb-4"} "Clojure Sample Project"]
   [:p.lead "This is a sample application demonstrating the authentication flow, using Clojure"]])

(def content-panel-data
  [{:title "Configure other identity providers"
    :url "https://auth0.com/docs/connections"
    :content "Auth0 supports social providers as Facebook, Twitter, Instagram and 100+, Enterprise providers as Microsoft Office 365, Google Apps, Azure, and more. You can also use any OAuth2 Authorization Server."}
   {:title "Configure other identity providers"
    :url "https://auth0.com/docs/connections"
    :content "Auth0 supports social providers as Facebook, Twitter, Instagram and 100+, Enterprise providers as Microsoft Office 365, Google Apps, Azure, and more. You can also use any OAuth2 Authorization Server."}
   {:title "Configure other identity providers"
    :url "https://auth0.com/docs/connections"
    :content "Auth0 supports social providers as Facebook, Twitter, Instagram and 100+, Enterprise providers as Microsoft Office 365, Google Apps, Azure, and more. You can also use any OAuth2 Authorization Server."}
   {:title "Configure other identity providers"
    :url "https://auth0.com/docs/connections"
    :content "Auth0 supports social providers as Facebook, Twitter, Instagram and 100+, Enterprise providers as Microsoft Office 365, Google Apps, Azure, and more. You can also use any OAuth2 Authorization Server."}])

(defn icon [name]
  [:i {:class (str "mr-2 fa fa-" name)}])

(def link-icon
  (icon "link"))

(defn- content-panel [{url :url
                       title :title
                       content :content}]
  [:div.content-panel
   [:h5 {:class "mb-3"}
    (link-to url link-icon title)]
   [:p content]])

(def index-content
  [:div {:class "d-flex justify-content-between flex-wrap"}
   (map content-panel content-panel-data)])

(defn index [session]
  (layouts/default "Auth0 Clojure Sample"
    index-hero
    [:div {:class "next-steps"}
     [:h2 {:class "mt-5 text-center"} "What can I do?"]
     index-content
     session]))