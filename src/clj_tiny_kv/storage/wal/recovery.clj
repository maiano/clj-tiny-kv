(ns clj-tiny-kv.storage.wal.recovery
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]))

(defn valid-line? [line]
  (try
    (edn/read-string line)
    true
    (catch Exception _ false)))

(defn replay-wal [file]
  (with-open [rdr (io/reader file)]
    (->> rdr
         line-seq
         (filter valid-line?)
         (map edn/read-string))))
