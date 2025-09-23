(ns fp-lab-1.task5.inf-seq
  (:require
   [fp-lab-1.util.math :refer [lcm]]))

(defn smallest-multiple [n]
  (->> (iterate inc 1)
       (reductions lcm 1)
       (take n)
       (last)))