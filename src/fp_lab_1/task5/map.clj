(ns fp-lab-1.task5.map
  (:require
   [fp-lab-1.util.math :refer [prime?]]))

(defn max-power
  "Возводит число a в маскимальную степень пока оно меньше числа n"
  [a n]
  (->> (iterate #(* a %) 1)
       (take-while #(< % n))
       (last)))

(defn smallest-multiple [n]
  (->> (range (inc n))
       (filter prime?)
       (map #(max-power % n))
       (reduce * 1)))