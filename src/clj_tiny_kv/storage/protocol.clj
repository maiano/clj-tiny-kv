(ns clj-tiny-kv.storage.protocol)

(defprotocol Storage
  (load! [this])

  (put! [this k v])

  (get-value [this k])

  (delete! [this k])

  (dump [this])

  (close! [this]))