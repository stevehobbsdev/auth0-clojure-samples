(ns auth0-clojure-sample.components.navbar)

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

(defn anonymous-menu []
  [:a#qsLoginBtn.btn.btn-primary.btn-margin {:href "/login"} "Log in"])

(defn user-menu [profile]
  (println profile)
  (str "Logged in as " (:name profile)))

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
       (nav-list-item
        (if profile (user-menu profile) (anonymous-menu)))]]]]])
