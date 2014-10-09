(ns osrm-clj.core-test
  (:require [clojure.test :refer :all]
            [osrm-clj.core :refer :all]))

(def south-boston {:latitude 36.670296M :longitude -78.9292835M})

(def elizabethtown {:latitude 40.1614075M :longitude -76.6563492})

(deftest route-test
  (testing "routes between two known valid points"
    (let [resp @(route south-boston elizabethtown)]
      (is (= 0 (:status resp))))))
