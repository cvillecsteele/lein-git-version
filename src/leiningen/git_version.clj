(ns leiningen.git-version
  (:require [leiningen.help]
            [leiningen.jar]
            [leiningen.compile]
            [leiningen.core.main]
            [leiningen.core.project]
            [robert.hooke]
            [leiningen.test]
            [clojure.string :as str])
  (:use
   [clojure.java.shell :only [sh]]))

(def defaults
  {:describe-command  ["git" "describe" "--match" "v*.*"
                      "--abbrev=4" "--dirty=**DIRTY**"]
   :tag->version '(fn [tag] (apply str (rest tag)))})

(defn get-git-version
  [& [config]]
  (let [{:keys [tag->version describe-command]} (merge defaults config)]
    ((eval tag->version) (str/trim (:out (apply sh describe-command))))))

(defn git-version
  "Show git project version"
  [project & args]
  (println (get-git-version (:git-version project))))
