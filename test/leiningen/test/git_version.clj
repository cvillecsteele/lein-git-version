(ns leiningen.test.git-version
  (:use leiningen.git-version)
  (:use [clojure.test]
        [midje.sweet]))

(facts
  (re-find #"1.0.*" (get-git-version {})) => string?
  (count (get-git-ref {})) => 40
  (re-matcher #"commit.*\nAuthor.*\nDate.*" (get-git-last-message {})) => truthy)

