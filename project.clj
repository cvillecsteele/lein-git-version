(defproject org.clojars.cvillecsteele/lein-git-version "1.1.0"
  :description "Use git for project versions"
  :url "https://github.com/cvillecsteele/lein-git-version"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  :profiles
  {:dev {:dependencies [[midje "1.8.3"]]
         :plugins [[lein-midje "3.2"]]}})
