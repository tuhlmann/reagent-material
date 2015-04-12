(ns reagent-ui.handler
  (:require [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.reload :as reload]
            [compojure.response :refer [render]]
            [ring.util.response :refer [response redirect content-type]]
            [clojure.java.io :as io]
            [environ.core :refer [env]]
            [noir.session :refer [wrap-noir-session]]))


(defn- form-params [request]
  (merge (:form-params request)
         (:multipart-params request)))

(def ring-defaults-config (assoc-in ring.middleware.defaults/site-defaults [:security :anti-forgery]
  {:read-token (fn [request]
    (or (-> request form-params (get "__anti-forgery-token"))
        (-> request :headers (get "x-csrf-token"))
        (-> request :headers (get "x-xsrf-token"))
        (-> request :params :csrf-token)))}))


(defn the-application [handler is-dev?]
  (if is-dev?
    (-> handler
      (wrap-defaults ring-defaults-config)
      wrap-exceptions
      reload/wrap-reload)
    (-> handler
      (wrap-defaults ring-defaults-config))))

