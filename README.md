# Лабораторная работа №1

Выполнил: Ступин Тимур Русланович

Группа: P3308

Преподаватель: Пенской Александр Владимирович

---

> [Проект Эйлер](https://projecteuler.net/archives), задача [№5](https://projecteuler.net/problem=5) и [№26](https://projecteuler.net/problem=26)

---

## Проблема №5

- **Название**: Smallest Multiple

- **Описание**: $2520$ is the smallest number that can be divided by each of the numbers from $1$ to $10$ without any remainder.

- **Задание**: What is the smallest positive number that is evenly divisible by all of the numbers from $1$ to $20$ ?

### Идея решения

Фактически задача сводится к тому чтобы найти наименьшее общее кратное (НОК) чисел от 1 до 20. Для этого в отдельном модуле [`math`](/src/fp_lab_1/util/math.clj) я реализовал две вспомогательные функции:

```clj
(defn gcd
  "Функция вычисления НОД двух чисел"
  [a b]
  (if (zero? b)
    a
    (gcd b (mod a b))))
```

```clj
(defn lcm
  "Функция вычисления НОК двух чисел"
  [a b]
  (/ (* a b) (gcd a b)))
```

Есть также и альтернативный способ.

Для того чтобы найти НОК всех чисел от $1$ до $N$ нужно:

- найти все простые числа от $1$ до $N$

- возвести каждое из этих чисел в максимально возможную степень так, чтобы результат возведения не превыщал число $N$

- перемножить все результаты возведения

Я использовал этот подход в реализации с `map`. Он использует функцию `prime?` которая проверяет число на прототу и также реализована в модуле [`math`](/src/fp_lab_1/util/math.clj):

```clj
(defn prime?
  "Функция проверки числа на простоту"
  [n]
  (cond
    (<= n 1) false
    (= n 2) true
    (even? n) false
    :else (not-any? #(zero? (mod n %)) (range 3 (inc (Math/sqrt n)) 2))))
```

### Монолитная реализация с использованием хвостовой рекурсии

Алгоритм простой:

- переменная `i` и `result` инициализируются `1`

- если `i > n` возращаем `result`

- иначе возвращаемся к началу цикла, присвоив `i` значение `i + 1` а result значение `НОК(result, i)`

Для реализации хвостовой рекурсии используется конструкция `loop` и `recur` так как компилятор Clojure сам по себе не производит эту оптимизацию.

```clj
(defn smallest-multiple [n]
  (loop [i 1 result 1]
    (if (> i n)
      result
      (recur (inc i) (lcm result i)))))
```

[Файл с решением](/src/fp_lab_1/task5/tail_rec.clj)

### Простое рекурсивное решение

Здесь всё предельно просто и прямолинейно:

- если `n = 1` то возвращаем `1`

- иначе возвращаем HOK от `n` и рекурсивного вызова с `n - 1`

```clj
(defn smallest-multiple [n]
  (if (= n 1)
    1
    (lcm n (smallest-multiple (dec n)))))
```

[Файл с решением](/src/fp_lab_1/task5/simple_rec.clj)

### Модульная реализация

Здесь я применил функции `reduce` и `range` для генерации последовательности чисел и применения к ним свёрки через функцию НОК.

Также я использовал макрос `->>` который позволяет привести вложенное применения функций к более читаемому виду.

```clj
(defn smallest-multiple [n]
  (->> (range 1 (inc n))
       (reduce lcm 1)))
```

[Файл с решением](/src/fp_lab_1/task5/module.clj)

### Генерация последовательности через `map`

Здесь я как раз решил применить альтернативный алгоритм решения поставленной задачи. Идея в следующем:

- при помощи range генерируется последовательность чисел от `0` до `n`

- далее при помощи `filter` по `prime?` из неё выбираются только простые числа

- после чего применяется `map`, который применяет к каждому числу функцию `max-power`, которая выполняет описанное в алгоритме возведение в максимальную степень

- наконец производится перемоножение всех чисел при помощи `reduce`

Код описанной функции:

```clj
(defn smallest-multiple [n]
  (->> (range (inc n))
       (filter prime?)
       (map #(max-power % n))
       (reduce * 1)))
```

И также код функции `max-power`:

```clj
(defn max-power
  "Возводит число a в маскимальную степень пока оно меньше числа n"
  [a n]
  (->> (iterate #(* a %) 1)
       (take-while #(< % n))
       (last)))
```

[Файл с решением](/src/fp_lab_1/task5/map.clj)

### Работа со спец синтаксисом циклов

В языке clojure есть функция `doseq`, позволяющая выполнять определённый действия итеративно, не создавая при этом никаких последовательностей. Она наиболее приближена к определению цикла и обычных языков программирования.

Идея алгоритма в данном случае не меняется, однако теперь накопление результат происходит в переменной `result`, которая в силу особенностей языка Clojure является атомом `atom`.

```clj
(defn smallest-multiple [n]
  (let [result (atom 1)]
    (doseq [i (range 1 (inc n))]
      (swap! result #(lcm % i)))
    @result))
```

[Файл с решением](/src/fp_lab_1/task5/cycles.clj)

### Работа с бесконечными списками

Тут я фактически применил идею решения из модульной реализации, однако заменил конечный `range` на бесконечный `iterate` а `reduce` на `reductions`. Фактически такая реализация позволяет генерировать бесконечную последовательно из НОК чисел от `1` до `n`. Для получения ответа я просто ограничиваю её 20-ю элементами и беру последний.

```clj
(defn smallest-multiple [n]
  (->> (iterate inc 1)
       (reductions lcm 1)
       (take n)
       (last)))
```

[Файл с решением](/src/fp_lab_1/task5/inf_seq.clj)

### Решение на python

```python
def gcd(a, b):
    if b == 0:
        return a
    return gcd(b, a % b)

def lcm(a, b):
    return a * b // gcd(a, b)

def smallest_multiple(n):
    ans = 1
    for i in range(1, n + 1):
        ans = lcm(ans, i)
    return ans
```

[Файл с решением](/py/task5.py)

## Проблема №26

- **Название**: Reciprocal Cycles

- **Описание**: A unit fraction contains $1$ in the numerator. The decimal representation of the unit fractions with denominators $2$ to $10$ are given:

    ```txt
    1/2 = 0.5
    1/3 = 0.(3)
    1/4 = 0.25
    1/5 = 0.2
    1/6 = 0.1(6)
    1.7 = 0.(142857)
    1/8 = 0.125
    1/9 = 0.(1)
    1/10 = 0.1
    ```

    Where $0.1(6)$ means $0.166666...$ , and has a $1$-digit recurring cycle. It can be seen that $1/7$ has a $6$-digit recurring cycle.

- **Задание**: Find the value of $d < 1000$ for which $1/d$ contains the longest recurring cycle in its decimal fraction part.

### Идея решения

Единственное что приходит в голову: максимально прямолинейно проверить то что спрашивают. Для этого нужно научится генерировать последовательноть из чисел после запятой при делении. Самая надёжная и при этом наивная реализация это деление уголком как в школе.

Для начала заметим, что к возникновению цикла приводит повторение остатков от деления, поэтому хранить сами числа нет никакой необходимости. Будем работать только с остатками. Для каждого вычисленного остатка будем запоминать его "позицию" при помощи мапы. Дальше достачно проверять не встречался ли уже вычисленный остаок, и если это так то просто считаем разность текущей и ранее вычисленной позиции и выходим. Формула вычисления очередного остатка предельно проста:

```python
new_reminder = (old_reminder * 10) % n
```

### Монолитная реализация с использованием хвостовой рекурсии

Как и раньше для хвостовой рекурсии применяются `loop` и `recur`. В основной функции перебираются числа, во вспомогательной `cycle-length` по описанному выше алгоритму вычисляется длинна цикла.

```clj
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
```

[Файл с решением](/src/fp_lab_1/task26/tail_rec.clj)

### Простое рекурсивное решение

Всё аналогично решению выше но теперь без хвостовой оптимизации

```clj
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
```

[Файл с решением](/src/fp_lab_1/task26/simple_rec.clj)

### Модульная реализация

Как можно было заметить ранее, формула вычисления остатков очень рекурсивная, поэтому здесь прямо напрашивалось использование метода `iterate`, который позволил получить вот такой лаконичный метод генерации последовательности остатков:

```clj
(iterate #(mod (* % 10) n) 1)
```

Основываясь на нём я написал новую реализацию функции `cycle-length`, основанную на всё той же идее но уже с последовательностями:

```clj
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
```

Эта функция применяется как в текущем решении, так и в решениях с `map` и  бесконечными последовательностями.

Идея же самого модульного решения фактически состоит в генерации последовательности числе через range, маппинге её на длинны циклов и поиск максимума.

```clj
(defn max-length [max-pair cur-pair]
  (if (> (second cur-pair) (second max-pair))
    cur-pair
    max-pair))

(defn reciprocal-cycles [n]
  (->> (range 1 (inc n))
       (map #(vector % (cycle-length %)))
       (reduce max-length [1 0])
       (first)))
```

[Файл с решением](/src/fp_lab_1/task26/module.clj)

### Генерация последовательности через `map`

Решение фактически дублирует предыдущее, так как там уже применялся `map` для генерации последовательности

```clj
(defn max-length [max-pair cur-pair]
  (if (> (second cur-pair) (second max-pair))
    cur-pair
    max-pair))

(defn reciprocal-cycles [n]
  (->> (range 1 (inc n))
       (map #(vector % (cycle-length %)))
       (reduce max-length [1 0])
       (first)))
```

[Файл с решением](/src/fp_lab_1/task26/map.clj)

### Работа со спец синтаксисом циклов

Здесь используется `do-seq` при помощи которого происходят все итерации и `atom` для сохранения результатов.

```clj
(defn cycle-length [n]
  (let [used (atom {})
        remainder (atom 1)
        position (atom 0)
        result (atom 0)]
    (doseq [_ (range n)]
      (let [current-remainder @remainder]
        (if (zero? current-remainder)
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
```

[Файл с решением](/src/fp_lab_1/task26/cycles.clj)

### Работа с бесконечными списками

Решение повторяет модульное, за исключением способа генерации последовательности чисел, которая теперь бесконечная:

```clj
(defn reciprocal-cycles [n]
  (->> (iterate inc 1)
       (map #(vector % (cycle-length %)))
       (take n)
       (reduce max-length [1 0])
       (first)))
```

[Файл с решением](/src/fp_lab_1/task26/inf_seq.clj)

### Решение на python

```python
def cycle_length(n):
    reminder = 1
    position = 0
    used = {}
    
    while reminder != 0:
        if reminder in used:
            return position - used[reminder]
        used[reminder] = position
        reminder = (reminder * 10) % n
        position += 1
    
    return 0

def reciprocal_cycles(n):
    max_length = 0
    value = 1
    for i in range(1, n + 1):
        cur_length = cycle_length(i)
        if cur_length > max_length:
            max_length = cur_length
            value = i
    return value
```

[Файл с решением](/py/task26.py)

## Вывод

В ходе работы я гораздо ближе познакомился с синтаксисом языка Clojure и его особенностями. Для себя я понял что с одной стороны функциональное программирование это не так страшно и даже интересно, но с другой иногда возникает желание выкинуть ноутбук в окно. Если сравнивать решения поставленных задач на Clojure и pyhton, то могу отметить что некоторые реализации на Clojure выглядят очень красиво и лаконично, в особенности варианты с последовательнотями. Однако любые попытки внести императив приводят к очень непрятным последствиям в виде резкого снижения читаемости и эффективности кода.
