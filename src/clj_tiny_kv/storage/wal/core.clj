(ns clj-tiny-kv.storage.wal.core
  (:require
   [clj-tiny-kv.storage.wal.file-logger :refer [->FileTransactionLogger
                                                open-writer]])
  (:import
   [java.util.concurrent.atomic AtomicLong]))

(defn create-file-logger [path]
  (let [file (java.io.File. path)
        writer (open-writer file)
        counter (AtomicLong. 0)]
    (->FileTransactionLogger file writer counter)))
