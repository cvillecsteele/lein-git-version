(ns lein-git-version.plugin
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer (pprint)]
            [clojure.string :as str]
            [leiningen.core.main]
            [leiningen.install]
            [leiningen.jar :as jar]
            [leiningen.git-version :refer [get-version]]
            [robert.hooke :refer (add-hook)]))

(def ^:private default-keys {:assoc-in-keys [[:version]]
                             :filepath nil
                             :filename "version.clj"})
                   
(defn- version-file [{:keys [git-version group name source-paths]}]
  (let [group (str/replace group "-" "_")
        name (str/replace name "-" "_")
        config (merge default-keys git-version)
        srcpath (first (filter seq (map #(re-find #".*src$" %) source-paths)))
        filename (:filename config)
        filepath (:filepath config)
        pathvec (if (seq filepath)
                  (conj [] 
                        srcpath
                        filepath
                        filename)
                  (conj [] 
                        srcpath 
                        (if (seq group) (str/replace group "." "/"))
                        name
                        filename))]
    (->> pathvec
         (filter seq)
         (interpose "/")
         (apply str))))

(defn- write-to-version-file [project]
  (let [{:keys [group name git-version]} project
        config (merge default-keys git-version)
        filepath (:filepath config)
        nsvec (if (seq filepath)
                (conj [] (str/replace filepath "/" ".") "version")
                (conj [] group name "version"))
        namespace (->> nsvec
                       (filter seq)
                       (interpose ".")
                       (apply str))
        code [";; Do not edit.  Generated by lein-git-version plugin."
              (str "(ns " namespace ")")
              ""
              (str "(def version \"" (get-version project true) "\")")
              ""]]
    (spit (version-file project) (str/join "\n" code))
    project))

(defn- retain-whitelisted-keys [f & [new original]]
  (merge new (select-keys original (into jar/whitelist-keys [:version :manifest]))))

(defn hooks[]
  (add-hook #'leiningen.jar/retain-whitelisted-keys #'retain-whitelisted-keys))

(defn middleware [{:keys [git-version] :as project}]
  (let [config (merge default-keys git-version)
        version (get-version project)
        project (reduce #(assoc-in %1 %2 version) project (:assoc-in-keys config))]
    (write-to-version-file project)))
