(ns fp-lab-1.task26.simple-rec)

(defn cycle-length [n]
  (letfn [(find-cycle [remainder seen position]
            (if (= remainder 0)
              0
              (if (contains? seen remainder)
                (- position (get seen remainder))
                (find-cycle (mod (* remainder 10) n)
                            (assoc seen remainder position)
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