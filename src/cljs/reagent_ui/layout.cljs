(ns reagent-ui.layout
  (:require [reagent.core :as r :refer [atom]]
            [reagent.impl.util :as util]
            [reagent.interop :refer-macros [.' .!]]
            [material-ui.core :as ui :include-macros true]
            [taoensso.encore  :as enc :refer [logf]]))


(def app-state (r/atom {
                :left-nav {
                           :open true
                           }
                 :name {
                        :first "Hi"
                        :last ""
                        }
                }))

(def refresh-left-nav (atom false))

(def menu-items [
                   {:payload "home" :text "Home" :icon "action-home"}
                   {:payload "favorites" :text "Favorites" :icon "action-favorite"}
                   {:payload "about" :text "About" :icon "action-info"}
                   ])

;(defn left-nav-wrapper [nav]
;  (let [wrapper (with-meta nav
;                           {:get-initial-state (fn [this]
;                                                 (js/console.log "GetInitialState")
;                                                 (r/set-state this {:open true}))
;                            :component-will-mount #(logf "will mount")
;                            :component-will-update #(logf "will update")
;                            :component-did-mount (fn [c] (js/console.log "did mount" c)
;                                                   (r/set-state c {:open true})
;                                                   (js/console.log "LeftNav: " (.' c -refs.leftNav))
;                                                   (-> (.' c -refs.leftNav) .open)
;                                                   )
;                            :component-did-update #(logf "did update")
;                            })]
;    (fn []
;       [wrapper])))
;
;(defn left-nav-component []
;  (let [this (r/current-component)]
;    [ui/LeftNav {:ref "leftNav"
;                 :docked false
;                 :menuItems menu-items
;                 :on-change (fn [event sel-idx payload]
;                              (js/console.log "Please close that thing 2 " sel-idx payload (r/props this))
;                              (r/set-state this {:docked false}))
;                 }]))


(defn text-field2 []
  (let [this (r/current-component)
        value (r/atom "Hi")]
    (r/create-element (aget js/MaterialUI "TextField")
                  #js{:className "foo" :value (get-in @app-state [:name :first])
                      :onChange (fn [el]
                                  (println "on change")
                                  ;(swap! value (fn [_] (str "Puhh")))
                                  (js/console.log (-> el .-target .-value))
                                  (swap! app-state assoc-in [:name :first] (-> el .-target .-value))
                                  (println "on change2 " @value)
                                  (r/flush)
                                  )}
                  )))

(defn left-nav-component []
  (r/create-class {
    ;:component-did-mount #(-> (.' % -refs.leftNav) .open)
    :component-will-update
                   (fn [c new-args]
                     (js/console.log "Children " (r/children c))
                     (-> (.' c -refs.leftNav) .toggle))
    :render
      (fn [this]
        [ui/LeftNav {:ref "leftNav"
                     :docked false
                     :open @refresh-left-nav
                     :menu-items menu-items
                     :on-change (fn [event sel-idx payload]
                                  (-> (.' this -refs.leftNav) .toggle)
                                  )
                     :onNavClose (fn []
                                     (js/console.log "onNavClose "))
                     }])}))

(defn main [data]
  [ui/AppCanvas {:predefinedLayout 1}
   [ui/AppBar {:class                    "mui-dark-theme"
               :title                    "Reagent Material Example"
               :zDepth                   0
               :onMenuIconButtonTouchTap (fn []
                                           (swap! app-state assoc-in [:left-nav :open] true)
                                           (println "open left nav: " (get-in @app-state [:left-nav :open]))
                                           (swap! refresh-left-nav not)
                                           ;(-> (.' left-nav -refs.leftNav) .open)
                                           )}
    [:div.action-icons
     [ui/IconButton {:iconClassName "mdfi_navigation_more_vert"}]
     [ui/IconButton {:iconClassName "mdfi_action_favorite_outline"}]
     [ui/IconButton {:iconClassName "mdfi_action_search"}]]]
   [left-nav-component]
   [:div.mui-app-content-canvas
    [:span "RouteHandler Here"]
    [:br]
    [ui/TextField {:hintText "Please enter your first name"
                   :floatingLabelText "First Name"
                   :value (get-in @app-state [:name :first])
                   :on-change #(swap! app-state assoc-in [:name :first] (-> % .-target .-value))
                   }]
    [:br]
    [ui/TextField {:hintText "Please enter your last name"
                   :value (get-in @app-state [:name :last])
                   :on-change #(swap! app-state assoc-in [:name :last] (-> % .-target .-value))
                   :floatingLabelText "Last Name"}]
    [:br]
    [:input.form-control {
                          :type      "text"
                          :value     (get-in @app-state [:name :first])
                          :on-change #(swap! app-state assoc-in [:name :first] (-> % .-target .-value))}]
    [:input.form-control {
                          :type      "text"
                          :value     (get-in @app-state [:name :last])
                          :on-change #(swap! app-state assoc-in [:name :last] (-> % .-target .-value))}]
    ]])

