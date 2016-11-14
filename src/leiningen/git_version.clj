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

(def default-config
  {:version-cmd "git describe --match v*.* --abbrev=4 --dirty=**DIRTY**"
   :ref-cmd "git rev-parse --verify HEAD"
   :msg-cmd "git log -1 HEAD"
   :ts-cmd "git log -1 --pretty=%ct"
   :root-ns nil
   :assoc-in-keys [[:version]]
   :filename "version.clj"
   :tag-to-version #(apply str (rest %))})

(defn get-git-version
  [{:keys [version-cmd tag-to-version] :as config}]
  (let [cmd (clojure.string/split version-cmd #" ")]
    (tag-to-version (clojure.string/trim (:out (apply sh cmd))))))

(defn get-git-ref
  [{:keys [ref-cmd] :as config}]
  (let [cmd (clojure.string/split ref-cmd #" ")]
    (apply str (clojure.string/trim (:out (apply sh cmd))))))

(defn get-git-last-message
  [{:keys [msg-cmd] :as config}]
  (let [cmd (clojure.string/split msg-cmd #" ")]
    (clojure.string/replace (apply str (clojure.string/trim
                                        (:out (apply sh cmd))))
                            #"\"" "'")))

(defn get-git-ts
  [{:keys [ts-cmd] :as config}]
  (let [cmd (clojure.string/split ts-cmd #" ")]
    (apply str (clojure.string/trim (:out (apply sh cmd))))))

(defn git-version
  "Show project version, as tagged in git."
  ^{:doc "Show git project version"}
  [project & args]
  (let [config (merge default-config git-version)]
    (println "Version:" (:version project) "\n" (get-git-last-message config))))

;; (get-git-version {:git-version {:version-cmd "git describe --abbrev=0"}})
;; (get-git-version {})

;; (get-git-ref {:git-version-cmd "git rev-parse"})
;; (get-git-ref {})

;; (get-git-last-message {})
;; (get-git-ts {})
