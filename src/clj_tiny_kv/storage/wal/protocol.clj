(ns clj-tiny-kv.storage.wal.protocol)

(defprotocol TransactionLogger
  (write-put! [this key value])
  (write-delete! [this key])
  (close! [this]))
