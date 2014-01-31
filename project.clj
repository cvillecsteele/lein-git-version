(defproject org.clojars.cvillecsteele/lein-git-version "1.0.2-SNAPSHOT"
  :description "Use git for project versions"
  :url "https://github.com/cvillecsteele/lein-git-version"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[midje "1.6.0"]
                                  [org.clojure/clojure "1.5.1"]]
                   :plugins [[lein-midje "3.1.3"]
                             [org.ozias.plugins/lein-git-version "1.0.2"]]}}
  :git-version {:append? false
                :version-command ["echo" "1.0.2-SNAPSHOT"]
                :version-file-command ["echo" "1.0.2-SNAPSHOT"]
                :filepath "lein_git_version"
                :assoc-in-keys [[:version]]}
  :eval-in-leiningen true)
