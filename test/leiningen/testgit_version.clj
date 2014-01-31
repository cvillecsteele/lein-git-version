(ns leiningen.testgit-version
  (:require [leiningen.git-version :refer (get-version)]
            [midje.config :as config :only (at-print-level)]
            [midje.sweet :refer :all]))

(def test-proj
  (atom
   {:version "1.0.0"
    :manifest {"Implementation-Version" "1.0.0"}
    :git-version {:version-command ["git" "rev-parse" "--short" "HEAD"]
                  :version-file-command ["git" "rev-parse" "HEAD"]
                  :assoc-in-keys [[:version] [:manifest "Implementation-Version"]]}}))

(defn- turn-off-append! []
  (swap! test-proj assoc-in [:git-version :append?] false))

(defn- echo-version-command! []
  (swap! test-proj assoc-in [:git-version :version-command] ["echo" "1.0.0"]))

(->> (facts "about get-version"
            (fact "build meta is appended (1.0.0+shortSHA)"
                  (re-matches #"1.0.0\+[a-f0-9]{4,7}" (get-version @test-proj)) => string?)
            (with-state-changes [(before :facts (turn-off-append!))]
              (fact "build meta is version (shortSHA)"
                    (re-matches #"[a-f0-9]{4,7}" (get-version @test-proj)) => string?)
              (with-state-changes [(before :facts (echo-version-command!))]
                (fact "version command is entire version (x.x.x)"
                      (re-matches #"[0-9]\.[0-9]\.[0-9]" (get-version @test-proj)) => string?))))
     (config/at-print-level :print-facts))
