(ns reagent-ui.components.web-server
  (:require
    [com.stuartsierra.component :as component]
    [taoensso.timbre            :as timbre]
    [org.httpkit.server         :refer [run-server]]
    [reagent-ui.handler         :refer [the-application]]
    [reagent-ui.site            :as site]))

(timbre/refer-timbre)

(defrecord HttpServer [port is-dev? server-stop]
  component/Lifecycle

  (start [component]
    (if server-stop
      component
      (let [component (component/stop component)
            handler (site/all-routes)

            server-stop (run-server (the-application handler is-dev?) {:port port})]
        (info "HTTP server started")
        (assoc component :server-stop server-stop))))

  (stop [component]
    (when server-stop (server-stop))
    (info "HTTP server stopped")
    (assoc component :server-stop nil)))


;; Constructor function
(defn new-http-server [port is-dev?]
  (map->HttpServer {:port port, :is-dev? is-dev?}))
