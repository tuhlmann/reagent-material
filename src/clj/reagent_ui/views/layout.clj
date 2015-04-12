(ns reagent-ui.views.layout
  (:require [hiccup.page            :refer [html5 include-css include-js]]
            [hiccup.element         :refer [link-to]]
            [hiccup.form            :refer :all]
            [clojure.string         :as str]
            [reagent-ui.dev         :refer [is-dev?]]))


(defn base [& content]
  (html5
    [:head
     [:title "Welcome to Reagent UI"]
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
     [:meta {:name "description" :content "An Invoicing application without the pain"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
     (include-css "/css/style.css")
    ]
    [:body content]))

(defn index [req]
  (base
    (include-js "/js/vendor.min.js")
    (include-js "/js/app.js")))

