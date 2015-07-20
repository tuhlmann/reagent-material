(defproject reagent-material "0.1.0-SNAPSHOT"
  :description "An example to get Material UI and
                ClojureScript Reagent working together"
  :url "http://github.com/tuhlmann/reagent-material"
  :license {:name "Undecided license"
            :url "http://www.I-dont-know.yet/license.txt"}

  :source-paths ["src/clj" "src/cljs"]

  :jvm-opts ["-XX:MaxPermSize=128M"]

  :dependencies [[org.clojure/clojure "1.7.0-beta1"]
                 [reagent "0.5.0" :exclusions [cljsjs/react]]
                 [reagent-forms "0.5.0"]
                 [reagent-utils "0.1.4"]
                 [secretary "1.2.3"]
                 [org.clojure/clojurescript "0.0-3169"]
                 [ring/ring-defaults "0.1.4"]
                 [metosin/ring-http-response "0.6.1"]
                 [com.stuartsierra/component "0.2.3"]
                 [org.clojure/tools.namespace "0.2.10"]
                 [prone "0.8.1"]
                 [compojure "1.3.3"]
                 [environ "1.0.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/core.cache "0.6.4"]
                 [org.clojure/core.match "0.2.2"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [com.cemerick/pomegranate "0.3.0"]
                 [lib-noir "0.9.9"]
                 [com.taoensso/timbre "3.4.0"]              ; Logging
                 [com.cognitect/transit-clj  "0.8.271"]
                 [com.cognitect/transit-cljs "0.8.207"]
                 [asset-minifier "0.1.6"]
                 [http-kit "2.1.19"]
                 [riddley "0.1.9"]
                 [clj-time "0.9.0"]
                 [clojure-watch "0.1.10"]]

  :plugins [
            [lein-cljsbuild "1.0.4"]
            [lein-environ "1.0.0"]
            [lein-ring "0.9.1"]
            [lein-asset-minifier "0.2.2"]
            [lein-sassy "1.0.7"]
            [lein-ancient "0.6.2"]
            [codox "0.8.10"]
            [lein-marginalia "0.8.0"]]

  :min-lein-version "2.5.0"

  :uberjar-name "reagent-material.jar"

  :main reagent-material.main

  :clean-targets ^{:protect false} ["resources/public/js"
                                    "resources/public/css"
                                    "target"]

  :sass {:src "src/scss"
         :dst "resources/public/css"
         :output-extension "css"
         :sourcemap :auto
         :style :compressed
         :syntax :scss
         :delete-output-dir true
         }

  :lein {
         :src ["src/less/styles.less"]
         :output-directory "resources/public/css"
         :source-maps true
         :command lessc
         :style :compressed
         }

  :minify-assets {
    :assets { "resources/public/js/vendor.min.js"
              ["resources/vendor-js/jquery/jquery-1.11.2.min.js"
               "resources/vendor-js/bootstrap/bootstrap.js"
               "resources/vendor-js/material-ui/material.min.js"
               "resources/vendor-js/material-ui/add-robo.js"]
            }
    ;:options {:optimization :none }
  }

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:preamble      ["resources/vendor-js/material-ui/material.js"]
                                        :output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        ;;:externs       ["react/externs/react.js"]
                                        ;;:source-map    "resources/public/js/out.js.map"
                                        :asset-path   "js/out"
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :profiles {:dev {:repl-options {:init-ns reagent-ui.main
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]
                                 }

                   :dependencies [[ring-mock "0.1.5"]
                                  [ring/ring-devel "1.3.2"]
                                  [leiningen "2.5.1"]
                                  [figwheel "0.2.5"]
                                  [weasel "0.6.0"]
                                  [com.cemerick/piggieback "0.1.6-SNAPSHOT"]
                                  [org.clojure/tools.nrepl "0.2.10"]
                                  [pjstadig/humane-test-output "0.7.0"]]

                   :source-paths ["env/dev/clj"]
                   :plugins [[lein-figwheel "0.2.3-SNAPSHOT"]
                             [com.cemerick/clojurescript.test "0.3.3"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]
                              }

                   :env {:dev? true}

                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                              :compiler {
                                                         :main "reagent-ui.dev"
                                                         :source-map-timestamp true
                                                         }
                                              }
                                        :test {:source-paths ["src/cljs"  "test/cljs"]
                                               :compiler {:output-to "target/test.js"
                                                          :optimizations :whitespace
                                                          :pretty-print true
                                                          :preamble ["react/react.js"]}}}
                               :test-commands {"unit" ["phantomjs" :runner
                                                       "test/vendor/es5-shim.js"
                                                       "test/vendor/es5-sham.js"
                                                       "test/vendor/console-polyfill.js"
                                                       "target/test.js"]}}}

             :uberjar {:source-paths ["env/prod/clj"]
                       :hooks [leiningen.cljsbuild leiningen.sass minify-assets.plugin/hooks]
                       :env {:production true}
                       :aot :all
                       :omit-source true
                       :cljsbuild {:jar true
                                   :builds {:app
                                             {:source-paths ["env/prod/cljs"]
                                              :compiler {
                                                         :optimizations :advanced
                                                         :pretty-print false
                                                         }
                                              }}}}})
