(ns clj-tiny-kv.storage.wal.core
  (:require
   [clj-tiny-kv.storage.wal.file-logger :refer [->FileTransactionLogger
                                                open-writer]]
   [clj-tiny-kv.storage.wal.recovery :as recovery])
  (:import
   [java.util.concurrent.atomic AtomicLong]))

(defn create-file-logger [path]
  (let [file (java.io.File. path)]
    (.mkdirs (.getParentFile file))
    (let [events (if (.exists file)
                   (recovery/replay-wal file)
                   [])
          last-seq (reduce (fn [acc e] (max acc (:seq e)))
                           0
                           events)
          counter (AtomicLong. last-seq)
          writer  (open-writer file)]
      (->FileTransactionLogger file writer counter))))

(defn replay [logger]
  (recovery/replay-wal (:file logger)))