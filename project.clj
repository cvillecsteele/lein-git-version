(defproject org.ozias.plugins/lein-git-version "1.0.2-SNAPSHOT"
  :description "Use git for project versions"
  :url "https://github.com/CraZySacX/lein-git-version"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :profiles {:dev {:dependencies [[midje "1.6.0"]
                                  [org.clojure/clojure "1.5.1"]]
                   :plugins [[lein-midje "3.1.3"]
                             [org.ozias.plugins/lein-git-version "1.0.2"]]}}
  :aliases {"package" ["do" "clean," "install"]
            "most" ["do" "clean," "doc," "package"]
            "dep" ["do" "deploy," "deploy" "clojars"]
            "all" ["do" "most," "dep"]}
  :jvm-opts ["-Xms1024m" "-Xmx1024m"]
  :deploy-repositories [["snapshots" 
                         {:url "http://www.ozias.net/artifactory/libs-snapshot-local"
                          :creds :gpg}]
                        ["releases"
                         {:url "http://www.ozias.net/artifactory/libs-release-local"
                          :creds :gpg}]]
  :scm {:name "git"
        :url "https://github.com/CraZySacX/lein-git-version"}
  :manifest {"Implementation-Version" "1.0.2-SNAPSHOT"}
  :git-version {:version-command ["git" "rev-parse" "--short" "HEAD"]
                :version-file-command ["git" "rev-parse" "HEAD"]
                :filepath "lein_git_version"
                :assoc-in-keys [[:version] [:manifest "Implementation-Version"]]}
  :eval-in-leiningen true)
