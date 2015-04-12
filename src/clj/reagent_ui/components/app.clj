(ns reagent-ui.components.app
  (:require [com.stuartsierra.component :as component]
            [taoensso.timbre            :as timbre]))

(timbre/refer-timbre)

(defrecord App []
  component/Lifecycle

  (start [component]
    (info "Application logic started")
    component)

  (stop [component]
    (info "Application logic stopped")
    component))

(defn new-app []
  (map->App {}))
