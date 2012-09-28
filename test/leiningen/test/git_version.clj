(ns leiningen.test.git-version
  (:use leiningen.git-version)
  (:use [clojure.test]
        [midje.sweet]))

(facts
  (re-find #"1.0.0" (get-git-version)) => "1.0.0")

