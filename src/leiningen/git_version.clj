(ns leiningen.git-version
  (:require [leiningen.help]
            [leiningen.jar]
            [leiningen.compile]
            [leiningen.core.main]
            [leiningen.core.project]
            [robert.hooke]
            [leiningen.test])
  (:use
   [clojure.java.shell :only [sh]]))

(defn get-git-version
  []
  (apply str (rest (clojure.string/trim
                    (:out (sh
                           "git" "describe" "--match" "v*.*"
                           "--abbrev=4" "--dirty=**DIRTY**"))))))

(defn git-version
  "Show project version, as tagged in git."
  ^{:doc "Show git project version"}
  [project & args]
  (println (get-git-version)))
