(ns leiningen.test.git-version
  (:use leiningen.git-version)
  (:use [clojure.test]
        [midje.sweet]))

(facts
 (re-find #"1.0.2" (get-git-version {})) => "1.0.2"
 (re-find #"7.8.9"
          (get-git-version {:git-version {:describe ["echo" "v7.8.9"]}})) => "7.8.9"
 (count (re-find #"^[0-9a-f]+$" (get-git-ref {}))) => 40
 (get-git-ref {:git-version {:ref ["echo" "0123456789abcdef"]}}) => "0123456789abcdef")
