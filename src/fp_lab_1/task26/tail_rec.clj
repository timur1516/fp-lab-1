(ns fp-lab-1.task26.tail-rec)

(defn cycle-length [n]
  (loop [remainder 1 used {} position 0]
    (if (zero? remainder)
      0
      (if (contains? used remainder)
        (- position (get used remainder))
        (recur (mod (* remainder 10) n)
               (assoc used remainder position)
               (inc position))))))

(defn reciprocal-cycles [n]
  (loop [d 1 max-d 1 max-length 0]
    (if (>= d n)
      max-d
      (let [length (cycle-length d)]
        (if (> length max-length)
          (recur (inc d) d length)
          (recur (inc d) max-d max-length))))))