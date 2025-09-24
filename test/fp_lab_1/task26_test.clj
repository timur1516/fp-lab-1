(ns fp-lab-1.task26-test
  (:require [clojure.test :refer [deftest testing is run-tests]]
            [fp-lab-1.task26.tail-rec :as tail-rec]
            [fp-lab-1.task26.simple-rec :as simple-rec]
            [fp-lab-1.task26.module :as module]
            [fp-lab-1.task26.map :as map]
            [fp-lab-1.task26.cycles :as cycles]
            [fp-lab-1.task26.inf-seq :as inf-seq]))

(deftest tail-rec-test
  (testing "Хвостовая рекурсия"
    (is (= (tail-rec/reciprocal-cycles 1000) 983))))

(deftest simple-rec-test
  (testing "Простая рекурсия"
    (is (= (simple-rec/reciprocal-cycles 1000) 983))))

(deftest module-test
  (testing "Модульная реализация с reduce"
    (is (= (module/reciprocal-cycles 1000) 983))))

(deftest map-test
  (testing "Генерация последовательностей с map"
    (is (= (map/reciprocal-cycles 1000) 983))))

(deftest cycles-test
  (testing "Спец синтаксис циклов"
    (is (= (cycles/reciprocal-cycles 1000) 983))))

(deftest inf-seq-test
  (testing "Бесконечные списки"
    (is (= (inf-seq/reciprocal-cycles 1000) 983))))

(run-tests)
