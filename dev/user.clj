(ns user
  (:require [clojure.tools.namespace.repl :as tn]
            [clj-tiny-kv.storage :as storage]
            [clojure.repl :as repl]))

(tn/set-refresh-dirs "src" "dev")

(defn reset []
  (tn/refresh))

(comment
  (storage/store)
  (repl/doc storage/store)
  (reset))