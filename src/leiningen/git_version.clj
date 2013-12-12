(ns leiningen.git-version
  (:require [clojure.java.shell :refer [sh]]
            [clojure.string :as str :refer [trim]]))

(def ^:private defaults
  {:version-command ["git" "describe" "--match" "v*.*" "--abbrev=4" "--dirty=**DIRTY**"]
   :out->version '(fn [tag] tag)})

(defn get-git-version [& [config]]
  (let [{:keys [version-command out->version]} (merge defaults config)]
    ((eval out->version) (str/trim (:out (apply sh version-command))))))

(defn git-version
  "Show git project version"
  [project & args]
  (println (get-git-version (:git-version project))))
