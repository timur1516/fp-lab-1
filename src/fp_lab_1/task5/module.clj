(ns fp-lab-1.task5.module
  (:require
   [fp-lab-1.util.math :refer [lcm]]))

(defn smallest-multiple [n]
  (->> (range 1 (inc n))
       (reduce lcm 1)))