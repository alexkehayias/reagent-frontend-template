(ns leiningen.new.reagent-frontend
  (:require [leiningen.new.templates
             :refer [renderer name-to-path ->files
                     sanitize sanitize-ns project-name]]
            [leiningen.core.main :as main]))

(def render (renderer "reagent_frontend"))

(defn template-data [name]
  {:full-name name
   :name (project-name name)
   :project-goog-module (sanitize (sanitize-ns name))
   :project-ns (sanitize-ns name)
   :sanitized (name-to-path name)})

(defn generate [name]
  (let [data (template-data name)]
    (->files
      data
      ["project.clj" (render "project.clj" data)]
      ["public/css/site.css" (render "public/css/site.css" data)]
      ["public/index.html" (render "public/index.html" data)]
      ["src/{{sanitized}}/core.cljs" (render "src/core.cljs" data)]
      ["env/dev/cljs/{{sanitized}}/dev.cljs" (render "env/dev/cljs/dev.cljs" data)]
      ["env/prod/cljs/{{sanitized}}/prod.cljs" (render "env/prod/cljs/prod.cljs" data)]
      ["LICENSE" (render "LICENSE" data)]
      ["README.md" (render "README.md" data)]
      [".gitignore" (render "gitignore" data)])))

(defn reagent-frontend [name]
  (main/info "Generating fresh 'lein new' Reagent frontend project.")
  (generate name))
