(ns osrm-clj.core
  (:require [org.httpkit.client :as http]
            [cheshire.core :as json]))

(def ^:dynamic *endpoint* "http://router.project-osrm.org")

(def default-options
  {:user-agent "osrm-clj"})

(def route-action "viaroute")

(defn encode-location
  [{:keys [latitude longitude]}]
  (str "loc=" latitude "," longitude))

(defn query-string-for
  [locations]
  (clojure.string/join "&" (map encode-location locations)))

(defn route-url-for
  [locations]
  (str *endpoint* "/" route-action "?" (query-string-for locations)))

;; PUBLIC API

(defn route
  "Return shortest route between N locations.

   Location: {:latitude 0.00M :longitude 0.00M}

   Returns a future whose dereferenced value is the route response."
  [& locations]
  (future (json/parse-string
           (:body @(http/get (route-url-for locations) {:as :text :timeout 120000})) true)))

(defn batch-route
  "Route N routes, returning responses in same order as input."
  [routes]
  (map #(deref (apply route %)) routes))
