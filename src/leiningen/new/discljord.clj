;; SPDX-License-Identifier: MIT
(ns leiningen.new.discljord
  (:require [leiningen.new.templates :refer [multi-segment sanitize-ns renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [java-http-clj.core :as http]
            [clojure.edn :as edn])
  (:import (java.time LocalDate)))

(def render (renderer "discljord"))

(defn fetch-latest-version [library]
  (-> "https://clojars.org/api/artifacts/"
      (str library)
      (http/get {:headers {"Accept" "application/edn"}})
      :body
      edn/read-string
      :latest_version))

(defn discljord
  "Generate a new discljord project"
  [name]
  (let [data {:name name
              :year (.. (LocalDate/now) getYear)
              :latest-clojure "1.11.1" ;; FIXME get dynamically somehow
              :latest-discljord (fetch-latest-version "com.github.discljord/discljord")
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

