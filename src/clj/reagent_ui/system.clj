(ns reagent-ui.system
  (:require [com.stuartsierra.component       :as component]
            [reagent-ui.components.web-server :as web-server]
            [reagent-ui.components.app        :as app]))

(defn component-system [config]
  (component/system-map
    :http-server
      (web-server/new-http-server (:port config) (:is-dev? config))
    :app
      (app/new-app) ))
