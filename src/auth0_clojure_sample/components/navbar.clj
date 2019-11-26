(ns auth0-clojure-sample.components.navbar
  (:require [hiccup.core :as hc]))

(def navbar-button
  [:button {:class "navbar-toggler"
            :type "button"
            :data-toggle "collapse"
            :data-target "#navbarNav"}
   [:span.navbar-toggler-icon]])

(defn- icon [name & [class args]]
  [:i (merge {:class (str "fa " "fa-" name " " class)} args)])

(defn- nav-list-item [args & content]
  [:li.nav-item args content])

(defn- nav-link [[url name]]
  [:a.nav-link {:href url} name])

(defn navbar-links [& links]
  [:ul.navbar-nav.mr-auto
   (->> links
        (filter #(true? (last %)))
        (map #(nav-list-item (nav-link %))))])

(defn profile-picture [url & [alt]]
  [:img.nav-user-profile.rounded-circle
   {:src url :alt (or alt "Profile picture") :style "width: 75px"}])

(defn anonymous-menu []
  (nav-list-item
   [:a#qsLoginBtn.btn.btn-primary.btn-margin {:href "/login"} "Log in"]))

(defn user-menu [profile]
  (nav-list-item {:class "dropdown"}
                 [:a.nav-link.dropdown-toggle {:id "profileDropDown" :data-toggle "dropdown" :href "#"}
                  (profile-picture (:picture profile))]
                 [:div.dropdown-menu.dropdown-menu-left
                  [:div.dropdown-header (:name profile)]
                  [:a.dropdown-item.dropdown-profile {:href "/profile"} (icon "user" "mr-3") "Profile"]
                  [:a.dropdown-item {:href "/logout"} (icon "power-off" "mr-3") "Log out"]]))

(defn html [profile]
  [:div.navbar-container
   [:nav.navbar.navbar-expand-md.navbar-light.bg-light
    [:div.container
     [:div.navbar-brand.logo]
     navbar-button
     [:div.collapse.navbar-collapse {:id "navbarNav"}
      (navbar-links
       ["/" "Home" true])
      [:ul.navbar-nav.d-none.d-md-block
       (if profile (user-menu profile) (anonymous-menu))]]]]])

(comment
  (hc/html (profile-picture "image.jpg"))
  (hc/html (profile-picture "image.jpg" "This is a demo picture"))
  (hc/html (nav-list-item "Hello"))
  (hc/html (nav-list-item {:class "test"} [:li.dropdown [:a {:href "#"} "This is a link"]]))
  (hc/html (user-menu {:picture "http://my-img" :name "Demo User"}))
  (hc/html (icon "test" "mr-3")))