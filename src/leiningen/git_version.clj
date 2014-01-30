(ns leiningen.git-version
  (:require [clojure.java.shell :refer [sh]]
            [clojure.string :as str :refer [trim]]))

(def ^:private defaults
  {:version-command ["git" "describe" "--match" "v*.*" "--abbrev=4" "--dirty=**DIRTY**"]
   :version-file-command ["git" "describe" "--match" "v*.*" "--abbrev=4" "--dirty=**DIRTY**"]
   :out->version '(fn [version tag] (str version "+" tag))})

(defn get-version [project]
  (let [config (:git-version project)
        version (:version project)
        {:keys [version-command out->version]} (merge defaults config)]
    ((eval out->version) version (str/trim (:out (apply sh version-command))))))

(defn get-file-version [project]
  (let [config (:git-version project)
        version (:version project)
        {:keys [version-file-command out->version]} (merge defaults config)]
    ((eval out->version) version (str/trim (:out (apply sh version-file-command))))))

(defn git-version
  "Show git project version"
  [project & args]
  (println (get-version project)))
