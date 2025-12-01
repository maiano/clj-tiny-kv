(ns clj-tiny-kv.storage.wal.file-logger
  (:require [clj-tiny-kv.storage.wal.protocol :refer [TransactionLogger]])
  (:import (java.io BufferedWriter FileWriter)))

(defn open-writer [file]
  (BufferedWriter. (FileWriter. file true)))

(defrecord FileTransactionLogger [file writer seq-counter]
  TransactionLogger

  (write-put! [_ key value]
    (let [seq-num (.incrementAndGet seq-counter)
          event {:seq seq-num
                 :event-type :put
                 :key key
                 :value value}]
      (.write writer (str (pr-str event) "\n"))
      (.flush writer)
      (.sync (.getFD ^FileWriter (.getOut writer)))
      event))

  (write-delete! [_ key]
    (let [seq-num (.incrementAndGet seq-counter)
          event {:seq seq-num
                 :event-type :delete
                 :key key}]
      (.write writer (str (pr-str event) "\n"))
      (.flush writer)
      (.sync (.getFD ^FileWriter (.getOut writer)))
      event)))

