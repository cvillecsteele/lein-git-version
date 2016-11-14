(ns leiningen.git-version
  (:require
   [leiningen.help]
   [leiningen.jar]
   [leiningen.compile]
   [leiningen.core.main]
   [leiningen.core.project]
   [robert.hooke]
   [leiningen.test])
  (:use
   [clojure.java.shell :only [sh]]))

(def default-version-cmd "git describe --match v*.* --abbrev=4 --dirty=**DIRTY**")
(def default-ref-cmd "git rev-parse --verify HEAD")
(def default-msg-cmd "git log -1 HEAD")
(def default-ts-cmd "git log -1 --pretty=%ct")
(def default-tag-to-version #(apply str (rest %)))

(defn get-git-version
  [{:keys [git-version] :as project}]
  (let [f (or (:tag-to-version git-version)
              default-tag-to-version)
        cmd (clojure.string/split (:version-cmd git-version default-version-cmd) #" ")]
    (f (apply str (rest (clojure.string/trim (:out (apply sh cmd))))))))

(defn get-git-ref
  [{:keys [git-version] :as project}]
  (let [cmd (clojure.string/split (:ref-cmd git-version default-ref-cmd) #" ")]
    (apply str (clojure.string/trim (:out (apply sh cmd))))))

(defn get-git-last-message
  [{:keys [git-version] :as project}]
  (let [cmd (clojure.string/split (:msg-cmd git-version default-msg-cmd) #" ")]
    (clojure.string/replace (apply str (clojure.string/trim
                                        (:out (apply sh cmd))))
                            #"\"" "'")))

(defn get-git-ts
  [{:keys [git-version] :as project}]
  (let [cmd (clojure.string/split (:ts-cmd git-version default-ts-cmd) #" ")]
    (apply str (clojure.string/trim (:out (apply sh cmd))))))

(defn git-version
  "Show project version, as tagged in git."
  ^{:doc "Show git project version"}
  [project & args]
  (println "Version:" (:version project) "\n" (get-git-last-message project)))

;; (get-git-version {:git-version {:version-cmd "git describe --abbrev=0"}})
;; (get-git-version {})

;; (get-git-ref {:git-version-cmd "git rev-parse"})
;; (get-git-ref {})

;; (get-git-last-message {})
;; (get-git-ts {})
