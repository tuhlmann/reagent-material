(ns reagent-ui.dev
    (:require [environ.core :refer [env]]))

(def is-dev? (env :dev?))

(if is-dev?
  (throw (Exception. (str "Production environment code is being loaded while the dev environment is active. "
                          "You likely have compiled class files lying around from an uberjar build. "
                          "Remove the target/ directory and try again."))))

(defn browser-repl []
  (throw (Exception. "Browser connected REPL is not available in prod mode")))

(defn start-figwheel []
  (throw (Exception. "Figwheel is not available in prod mode")))

(defn start-scss []
  (throw (Exception. "SCSS compiler is not available in prod mode")))

(defn run-minify []
  (throw (Exception. "minify-assets is not available in prod mode")))


(defn start []
  (throw (Exception. "reloadable system is not available in prod mode")))

(defn stop []
  (throw (Exception. "reloadable system is not available in prod mode")))

(defn run []
  (throw (Exception. "reloadable system is not available in prod mode")))

(defn reset []
  (throw (Exception. "reloadable system is not available in prod mode")))
