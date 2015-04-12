(ns reagent-ui.config
  (:require [environ.core :refer [env]]))

(defn get-config []
  {
   :port (Integer/parseInt (or (env :rente-port) "10555"))
   :is-dev? (or (env :dev?) false)
  })

;; System State

(defonce app-state (atom {}))

(defn C
  "Returns a named component from the current app state"
  ([component]
   (get @app-state component))
  ([component key]
   (let [res (get-in @app-state [component key])]
     res)))



