(ns leiningen.git-version
  (:require [clojure.java.shell :refer [sh]]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as str :refer [trim]]))

(def ^:private defaults
  {:version-command ["git" "describe" "--match" "v*.*" "--abbrev=4" "--dirty=**DIRTY**"]
   :version-file-command ["git" "describe" "--match" "v*.*" "--abbrev=4" "--dirty=**DIRTY**"]})

(defn get-version [project & file]
  (let [config (:git-version project)
        version (:version project)
        {:keys [version-command version-file-command]} (merge defaults config)
        cmd (if (seq file) version-file-command version-command)
        shortver (apply str (take-while #(not (= \+ %)) version))]
    (if (seq file)
      (str shortver "+" (str/trim (:out (apply sh cmd))))
      (str version "+" (str/trim (:out (apply sh cmd)))))))

(defn git-version
  "Show git project version"
  [project & args]
  (println (:version project)))
