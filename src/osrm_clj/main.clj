(ns osrm-clj.compute-distance
  (:require [osrm-clj.core :refer :all])
  (:gen-class))

(require '[clojure.data.csv :as csv])
(require '[clojure.java.io :as io])
(use 'osrm-clj.core)

(alter-var-root #'*endpoint* (constantly "http://ec2-54-172-126-102.compute-1.amazonaws.com:5000"))

(def source-dest-pairs
  (with-open [in (io/reader "/Users/trentstrong/Development/work/aquatic/input/source_destination_pairs.tsv")]
    (doall (csv/read-csv in :separator \tab))))

(def routes (map (fn [pair] [{:latitude (nth pair 2) :longitude (nth pair 3)} {:latitude (nth pair 4) :longitude (nth pair 5)}]) source-dest-pairs))

(def routes (rest routes))

(def route-resps (batch-route routes {:batch-size 10}))

(get-in resp [:route_summary :total_distance])

(defn -main
  [& args]
  )
