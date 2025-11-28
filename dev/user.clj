(ns user
  (:require [clojure.tools.namespace.repl :as tn]))

(tn/set-refresh-dirs "src" "dev")

(defn reset []
  (tn/refresh))

(comment
  ;; (storage/store)
  ;; (repl/doc storage/store)
  (reset))