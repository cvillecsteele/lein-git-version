(defproject lein-git-version "1.0.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  :profiles
  {:dev {:dependencies [[midje "1.4.0"]]
         :plugins [[lein-midje "2.0.0-SNAPSHOT"]]}})
