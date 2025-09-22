(defproject fp-lab-1 "1.0"
  :description "Решение простых задач на Clojure"
  :url "https://github.com/timur1516/fp-lab-1"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.12.2"]]
  :main ^:skip-aot fp-lab-1.core
  :target-path "target/%s"
  :plugins [[dev.weavejester/lein-cljfmt "0.13.1"],
            [jonase/eastwood "1.4.3"],
            [com.github.clj-kondo/lein-clj-kondo "0.2.5"]]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
