(ns leiningen.git-version
  (:require [leiningen.help]
            [leiningen.jar]
            [leiningen.compile]
            [leiningen.core.main]
            [leiningen.core.project]
            [robert.hooke]
            [leiningen.test]
            [clojure.set :refer [rename-keys]]
            [clojure.string :refer [split]])
  (:use
   [clojure.java.shell :only [sh]]))

(defn- to-kv [s]
  (-> (re-seq #"^([^\s:]+):?\s+(.+)$" s)
      first
      rest
      ((fn [[k v]] [(keyword (.toLowerCase k)) v]))))

(defn get-git-info
  []
  (->>
   (-> (sh "git" "log" "-n" "1")
       :out
       (split #"\n"))
   (take 3)
   (map to-kv)
   flatten
   (apply hash-map)))

(defn- get-annotated-tag
  []
  (apply str
         (rest (clojure.string/trim
                (:out (apply sh
                             ["git" "describe" "--match" "v*.*" "--abbrev=4" "--dirty=**DIRTY**"]))))))

(defn get-project-info
  [project]
  (let [git-version (get-annotated-tag)]
    (-> (merge (get-git-info)
               {:version (if (empty? git-version) (:version project) git-version)})
        (rename-keys {:commit :revision}))))

(defn get-git-version
  [project]
  (let [info (get-project-info project)]
    (:version info)))

(defn git-version
  "Main project task."
  ^{:doc "Show git project version."}
  [project & args]
  (println (get-git-version project)))
