(ns fp-lab-1.util.math)

(defn gcd
  "Функция вычисления НОД двух чисел"
  [a b]
  (if (zero? b)
    a
    (gcd b (mod a b))))

(defn lcm
  "Функция вычисления НОК двух чисел"
  [a b]
  (/ (* a b) (gcd a b)))

(defn prime?
  "Функция проверки числа на простоту"
  [n]
  (cond
    (<= n 1) false
    (= n 2) true
    (even? n) false
    :else (not-any? #(zero? (mod n %)) (range 3 (inc (Math/sqrt n)) 2))))