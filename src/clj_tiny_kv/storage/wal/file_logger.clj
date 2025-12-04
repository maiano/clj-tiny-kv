(ns clj-tiny-kv.storage.wal.file-logger
  (:require [clj-tiny-kv.storage.wal.protocol :refer [TransactionLogger]])
  (:import (java.io FileOutputStream OutputStreamWriter BufferedWriter)))

(defn open-writer [file]
  (let [fos (FileOutputStream. file true)
        osw (OutputStreamWriter. fos)
        bw  (BufferedWriter. osw)]
    {:writer bw
     :fos fos}))


(defrecord FileTransactionLogger [file writer-map seq-counter]
  TransactionLogger

  (write-put! [_ key value]
    (let [seq-num (.incrementAndGet seq-counter)
          event {:seq seq-num
                 :event-type :put
                 :key key
                 :value value}
          ^BufferedWriter w (:writer writer-map)
          ^FileOutputStream fos (:fos writer-map)]
      (.write w (str (pr-str event) "\n"))
      (.flush w)
      (.getFD fos)
      (.sync (.getFD fos))
      event))

  (write-delete! [_ key]
    (let [seq-num (.incrementAndGet seq-counter)
          event {:seq seq-num
                 :event-type :delete
                 :key key}
          ^BufferedWriter w (:writer writer-map)
          ^FileOutputStream fos (:fos writer-map)]
      (.write w (str (pr-str event) "\n"))
      (.flush w)
      (.sync (.getFD fos))
      event)))

