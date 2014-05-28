(ns lein-git-version.plugin
  (:require [leiningen.git-version :refer [get-project-info]]
            [clojure.string :refer [join]]))

(defn middleware
  [project & args]
  (let [info (get-project-info project)
        code (str
              ";; Do not edit.  Generated by lein-git-version plugin.\n"
              "(ns " (:name project) ".version)\n"
              "(def version \"" (:version info) "\")\n"
              "(def info {\n"
              (->> info
                   (map (fn [[k v]] (format "  %s \"%s\"" k v)))
                   (join "\n"))
              "\n})\n")
        filename (str (first (:source-paths project)) "/"
                      (:name project) "/version.clj")]
    (-> project
        (update-in [:injections] concat `[(spit ~filename ~code)])
        (assoc :version (:version info)))))
