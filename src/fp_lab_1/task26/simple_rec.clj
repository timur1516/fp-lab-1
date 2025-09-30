(ns fp-lab-1.task26.simple-rec)

(defn cycle-length [n]
  (letfn [(find-cycle [remainder used position]
            (if (zero? remainder)
              0
              (if (contains? used remainder)
                (- position (get used remainder))
                (find-cycle (mod (* remainder 10) n)
                            (assoc used remainder position)
                            (inc position)))))]
    (find-cycle 1 {} 0)))

(defn reciprocal-cycles [n]
  (letfn [(find-max [d max-d max-length]
            (if (>= d n)
              max-d
              (let [length (cycle-length d)]
                (if (> length max-length)
                  (find-max (inc d) d length)
                  (find-max (inc d) max-d max-length)))))]
    (find-max 1 1 0)))