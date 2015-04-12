(ns reagent-ui.site
  (:require [compojure.core           :refer [ANY GET POST routes]]
            [compojure.route          :refer [not-found resources]]
            [ring.handler.dump        :refer [handle-dump]]
            [reagent-ui.views.layout  :as layout]))

(defn on-error
  [request value]
  {:status 404
   :headers {}
   :body "Not authorized"})

(defn all-routes []
  (routes
    (GET "/"          [] layout/index)
    (resources "/")
    (ANY  "/r"        [] handle-dump)
    (not-found "<h1>Page not found</h1>")))
