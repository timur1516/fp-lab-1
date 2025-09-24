(ns fp-lab-1.task26.cycles)

(defn cycle-length [n]
  (let [used (atom {})
        remainder (atom 1)
        position (atom 0)
        result (atom 0)]
    (doseq [_ (range n)]
      (let [current-remainder @remainder]
        (if (= current-remainder 0)
          (do
            (reset! result 0)
            (reduced nil))
          (if (contains? @used current-remainder)
            (do
              (reset! result (- @position (get @used current-remainder)))
              (reduced nil))
            (do
              (swap! used assoc current-remainder @position)
              (swap! remainder #(mod (* % 10) n))
              (swap! position inc))))))
    @result))

(defn reciprocal-cycles [n]
  (let [max-d (atom 1)
        max-length (atom 0)]
    (doseq [d (range 1 n)]
      (let [length (cycle-length d)]
        (when (> length @max-length)
          (reset! max-d d)
          (reset! max-length length))))
    @max-d))
