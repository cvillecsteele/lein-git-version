(ns lein-git-version.plugin
  (:require [leiningen.git-version]))

(defn hooks []
  (leiningen.git-version/activate))
