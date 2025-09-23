(ns fp-lab-1.task5.tail-rec
  (:require
   [fp-lab-1.util.math :refer [lcm]]))

(defn smallest-multiple [n]
  (loop [i 1 result 1]
    (if (> i n)
      result
      (recur (inc i) (lcm result i)))))
