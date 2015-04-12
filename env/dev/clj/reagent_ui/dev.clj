(ns reagent-ui.dev
  (:require
    [cemerick.piggieback :as piggieback]
    [weasel.repl.websocket :as weasel]
    [leiningen.core.main :as lein]
    [environ.core :refer [env]]
    [asset-minifier.core :refer [minify]]
    [clojure-watch.core :refer [start-watch]]
    [clojure.java.shell :refer [sh]]))

(def is-dev? (env :dev?))

(defn browser-repl []
  (piggieback/cljs-repl :repl-env (weasel/repl-env :ip "0.0.0.0" :port 9001)))

(defn start-figwheel []
  (future
    (print "Starting figwheel.\n")
    (lein/-main ["figwheel"])))

(defn start-scss []
 (future
   (println "Starting scss.")
   (lein/-main ["sass" "auto"])
   ))


(defn assets-config []
  (->> "project.clj"
       slurp
       read-string
       (drop-while #(not= :minify-assets %))
       second))

(defn run-minify []
  (let [cfg (assets-config)
        assets (:assets cfg)
        options (:options cfg)]
    (future
      (println "Minifying assets.")
      (minify assets options))))

