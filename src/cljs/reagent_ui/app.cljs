(ns reagent-ui.app
  (:require [reagent.core           :as reagent]
            [reagent.session        :as session]
            [secretary.core         :as secretary :include-macros true]
            [taoensso.encore        :as enc    :refer (logf)]
            [reagent-ui.layout      :as layout]
            [goog.events            :as events]
            [goog.history.EventType :as EventType])
  (:import goog.History))

(defonce state (reagent/atom
                 {:title          "Reagent Material"
                  :messages       []
                  :re-render-flip false}))


;; -------------------------
; Routes
(secretary/set-config! :prefix "#")
;
(secretary/defroute "/" []
  (session/put! :current-page #'layout/main))

;(secretary/defroute "/about" []
;  (session/put! :current-page #'views/main2))

; (session/get :current-page)
(defn current-page [data]
  ;(:re-render-flip @data)
  [(session/get :current-page) data])

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))


(defn ^:export main []
  (hook-browser-navigation!)
  (reagent/render-component [current-page state] (.-body js/document)))
