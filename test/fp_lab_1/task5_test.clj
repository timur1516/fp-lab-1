(ns fp-lab-1.task5-test
  (:require [clojure.test :refer [deftest testing is run-tests]]
            [fp-lab-1.task5.tail-rec :as tail-rec]
            [fp-lab-1.task5.simple-rec :as simple-rec]
            [fp-lab-1.task5.module :as module]
            [fp-lab-1.task5.map :as map]
            [fp-lab-1.task5.cycles :as cycles]
            [fp-lab-1.task5.inf-seq :as inf-seq]))

(deftest tail-rec-test
  (testing "Хвостовая рекурсия"
    (is (= (tail-rec/smallest-multiple 20) 232792560))))

(deftest simple-rec-test
  (testing "Простая рекурсия"
    (is (= (simple-rec/smallest-multiple 20) 232792560))))

(deftest module-test
  (testing "Модульная реализация с reduce"
    (is (= (module/smallest-multiple 20) 232792560))))

(deftest map-test
  (testing "Генерация последовательностей с map"
    (is (= (map/smallest-multiple 20) 232792560))))

(deftest cycles-test
  (testing "Спец синтаксис циклов"
    (is (= (cycles/smallest-multiple 20) 232792560))))

(deftest inf-seq-test
  (testing "Бесконечные списки"
    (is (= (inf-seq/smallest-multiple 20) 232792560))))

(run-tests)
