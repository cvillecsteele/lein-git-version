(ns leiningen.git-version
  (:require [clojure.java.shell :refer [sh]]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as str :refer [trim]]))

(def ^:private defaults
  {:append? true
   :version-command ["git" "describe" "--match" "v*.*" "--abbrev=4" "--dirty=**DIRTY**"]
   :version-file-command ["git" "describe" "--match" "v*.*" "--abbrev=4" "--dirty=**DIRTY**"]})

(defn get-version [{:keys [git-version version]} & file]
  (let [{:keys [version-command version-file-command append?]} (merge defaults git-version)
        cmd (if (seq file) version-file-command version-command)
        basever (apply str (take-while #(not (= \+ %)) version))
        cmdver (str/trim (:out (apply sh cmd)))]
    (if append?
      (if (seq file)
        (str basever "+" cmdver)
        (str version "+" cmdver))
      (str cmdver))))

(defn git-version
  "Show git project version"
  [project & args]
  (println (:version project)))
