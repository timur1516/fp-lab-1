(ns fp-lab-1.task5.cycles
  (:require
   [fp-lab-1.util.math :refer [lcm]]))

(defn smallest-multiple [n]
  (let [result (atom 1)]
    (doseq [i (range 1 (inc n))]
      (swap! result #(lcm % i)))
    @result))
