(ns fp-lab-1.task26.inf-seq)

(defn cycle-length [n]
  (letfn [(rem-distance [seq]
            (reduce (fn [used [idx item]]
                      (if (zero? item)
                        (reduced 0)
                        (if (contains? used item)
                          (reduced (- idx (used item)))
                          (assoc used item idx))))
                    {} seq))
          (rem-seq [n] (iterate #(mod (* % 10) n) 1))]
    (->> (rem-seq n)
         (map-indexed list)
         (rem-distance))))

(defn max-length [max-pair cur-pair]
  (if (> (second cur-pair) (second max-pair))
    cur-pair
    max-pair))

(defn reciprocal-cycles [n]
  (->> (iterate inc 1)
       (map #(vector % (cycle-length %)))
       (take n)
       (reduce max-length [1 0])
       (first)))