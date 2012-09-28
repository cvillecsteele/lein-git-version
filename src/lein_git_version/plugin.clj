(ns lein-git-version.plugin
  (:use
   [leiningen.git-version :only [get-git-version]]))

(defn middleware
  [project]
  (assoc project :version (get-git-version)))
