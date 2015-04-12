(ns reagent-ui.main
  (:require
    [com.stuartsierra.component   :as component]
    [clojure.tools.namespace.repl :refer [refresh refresh-all]]
    [reagent-ui.config            :as config]
    [reagent-ui.system            :as system]
    [taoensso.timbre              :as timbre]
    [reagent-ui.dev               :refer [browser-repl run-minify start-figwheel start-scss]])
  (:gen-class))

(timbre/refer-timbre)

(defn add-shutdown-hook
  "Adds a handler to the system run when the JVM shuts down.
  Can be used to shut down other sub systems, like db connections or closing
  other resources."
  [the-system]
  (.addShutdownHook
    (Runtime/getRuntime)
    (Thread.
      (fn []
        (println "Running Shutdown Hook")
        (component/stop the-system)))))

(defn run-auto-reload []
  ;(auto-reload *ns*) ;This is the reload for html templates
  (run-minify)
  (start-scss)
  (start-figwheel))

;; Reloadable system

(defn init-system []
  (reset! config/app-state (system/component-system (config/get-config))))

(defn start []
  (info "start dev system")
  (swap! config/app-state component/start))

(defn stop []
  (swap! config/app-state (fn [s] (when s (component/stop s)))))

(defn run-dev-env []
  (run-auto-reload))

(defn run-application []
  (init-system)
  (start))

(defn run []
  (info "Start development system")
  (run-dev-env)
  (run-application))

(defn reset []
  (stop)
  (refresh :after 'reagent-ui.main/run-application))

(defn -main [& args]
  (run-application)
  (add-shutdown-hook (component/start (system/component-system (config/get-config))))
  (info "Reagent Material started"))
