(ns fp-lab-1.task5.simple-rec
  (:require
   [fp-lab-1.util.math :refer [lcm]]))

(defn smallest-multiple [n]
  (if (= n 1)
    1
    (lcm n (smallest-multiple (dec n)))))