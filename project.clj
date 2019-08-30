(defproject auth0-clojure-sample "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [com.auth0/auth0 "1.14.2"]]
  :repl-options {:init-ns user}
  :target-path "target/%s"
  :main auth0-clojure-sample.core
  :profiles {:dev {:source-paths ["dev" "src"]
                   :main auth0-clojure-sample.core/-dev-main}})
