(ns leiningen.test.git-version
  (:use
   [leiningen.git-version]
   [clojure.test]
   [midje.sweet])
  (:require
   [lein-git-version.plugin :as plugin]))

(def p
  {:description "Use git for project versions"
   :compile-path "/Users/colinsteele/Projects/lein-git-version/target/classes"
   :url "http://www.eclipse.org/legal/epl-v10.html"
   :resource-paths '("/Users/colinsteele/Projects/lein-git-version/dev-resources"
                     "/Users/colinsteele/Projects/lein-git-version/resources")
   :name "lein-git-version"
   :source-paths '("/Users/colinsteele/Projects/lein-git-version/src")
   :root "/Users/colinsteele/Projects/lein-git-version"
   :version "1.2.0"
   :git-version {}})

(facts
  (re-find #"1.2.*" (get-git-version default-config)) => string?
  (count (get-git-ref default-config)) => 40
  (re-matcher #"commit.*\nAuthor.*\nDate.*"
              (get-git-last-message default-config)) => truthy
  (let [cooked (plugin/middleware p)]
    cooked => contains {:injections anything
                        :gitref string?
                        :version string?}
    (re-find #"1.2.*" (:version cooked)) => string?))





