(ns auth0-clojure-sample.components.navbar
  (:require [hiccup.element :refer [link-to]])
  (:require [hiccup.core :as h]))

(def navbar-button
  [:button {:class "navbar-toggler"
            :type "button"
            :data-toggle "collapse"
            :data-target "#navbarNav"}
   [:span.navbar-toggler-icon]])

(defn- nav-list-item [& content]
  [:li.nav-item content])

(defn- nav-link [[url name]]
  [:a.nav-link {:href url} name])

(defn navbar-links [& links]
  [:ul.navbar-nav.mr-auto
   (->> links
        (filter #(true? (last %)))
        (map #(nav-list-item (nav-link %))))])

(def anonymous-menu
  (nav-list-item
   [:a#qsLoginBtn.btn.btn-primary.btn-margin {:href "/login"} "Log in"]))

(defn- user-image [profile]
  [:img {:src (profile :picture) :class "nav-user-profile rounded-circle" :style "width: 75px"}])

(defn- user-menu [profile]
  (user-image profile))

(defn html [profile]
  (println profile)
  [:div.navbar-container
   [:nav.navbar.navbar-expand-md.navbar-light.bg-light
    [:div.container
     [:div.navbar-brand.logo]
     navbar-button
     [:div.collapse.navbar-collapse {:id "navbarNav"}
      (navbar-links
       ["/" "Home" true])
      [:ul.navbar-nav.d-none.d-md-block
       (if profile
         (user-menu profile)
         anonymous-menu)]]]]])

(comment
  (h navbar)
  (h (navbar-links {:url "/"}))
  (h footer))