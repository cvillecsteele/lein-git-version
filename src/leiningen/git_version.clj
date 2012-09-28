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

(defn get-git-version
  []
  (apply str (rest (clojure.string/trim
                    (:out (sh
                           "git" "describe" "--match" "v*.*"
                           "--abbrev=4" "--dirty=**DIRTY**"))))))

(defn git-version
  "Main project task."
  ^{:doc "Show git project version"}
  [project & args]
  (println (get-git-version)))

(defn version-hook
  "Inject the version into the project map"
  [unhooked task-name project args]
  (unhooked task-name
            (assoc-in project [:version] (get-git-version))
            args))

(defn jar-filename-hook
  "Inject the version into the project map.  We have to do this
  because lein jar does weird stuff with eval'ing the project even
  though we've already injected the version."
  ([unhooked project]
     (unhooked (assoc-in project [:version] (get-git-version))))
  ([unhooked project uberjar?]
     (unhooked (assoc-in project [:version] (get-git-version))
               uberjar?)))

(defn activate
  "Hook the general task fn so it has the modified project version."
  []
  (robert.hooke/add-hook #'leiningen.jar/get-jar-filename jar-filename-hook)
  (robert.hooke/add-hook #'leiningen.core.main/apply-task version-hook))



