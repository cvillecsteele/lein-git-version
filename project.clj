(defproject org.ozias.plugins/lein-git-version "1.0.2-SNAPSHOT"
  :description "Use git for project versions"
  :url "https://github.com/cvillecsteele/lein-git-version"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  :profiles {:dev {:dependencies [[midje "1.6.0"]
                                  [org.clojure/clojure "1.5.1"]]
                   :plugins [[lein-midje "3.1.3"]]}})
