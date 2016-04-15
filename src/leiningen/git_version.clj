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

(def default-describe-command
  ["git" "describe" "--match" "v*.*" "--abbrev=4" "--dirty=**DIRTY**"])
(def default-ref-command
  ["git" "rev-parse" "--verify" "HEAD"])

(defn get-git-version
  [project]
  (apply str (rest (clojure.string/trim
                    (:out (apply sh (or (-> project :git-version :describe)
                                        default-describe-command)))))))

(defn get-git-ref
  [project]
  (clojure.string/trim
   (:out (apply sh (or (-> project :git-version :ref)
                       default-ref-command)))))

(defn git-version
  "Show project version, as tagged in git."
  ^{:doc "Show git project version"}
  [project & args]
  (println (get-git-version project)))
