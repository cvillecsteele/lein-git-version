# lein-git-version

I'm a big fan of DRY.  I already have version information in git for
my projects.  Remembering to maintain that information in my
project.clj is a PITA.  So... Here's a Leiningen plugin to obtain a
version identifier from git, using:

    git describe --match 'v*.*' --abbrev=4 --dirty=**DIRTY**

It uses annotated git tags (those set with `git tag -a`) as the
authoritative source of version information for your project.

It then substitutes that version into the project on the fly, so every
lein task that uses the project version sees the one obtained from
git.

## Usage

Put `[lein-git-version "1.0.0"]` into the `:plugins` vector
of your project.clj.

That's it.  The version string set in your project.clj will be
ignored, and all lein tasks relying on it will instead see the
git-ified version.

For example, with a project.clj that looks like this:

    (defproject nifty "bLAH BLaH"
      :description "Do nifty things"
      ...)

    $ git tag -a -m "First version!" v1.0.0
    $ lein git-version
    1.0.0

## Known issues



## License

Copyright © 2012 Colin Steele

Distributed under the Eclipse Public License, the same as Clojure.
