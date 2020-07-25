(ns leiningen.new.discljord
  (:require [leiningen.new.templates :refer [multi-segment sanitize-ns renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "discljord"))

(defn discljord
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :namespace (multi-segment (sanitize-ns name))
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' discljord project.")
    (->files data
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["project.clj" (render "project.clj" data)]
             ["config.edn" (render "config.edn")]
             "resources"
             [".gitignore" (render ".gitignore")]
             [".hgignore" (render ".hgignore")]
             ["LICENSE" (render "LICENSE")]
             ["README.md" (render "README.md" data)])))

