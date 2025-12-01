(ns clj-tiny-kv.storage.memory.core
  (:require
   [clj-tiny-kv.storage.protocol :refer [Storage]]
   [clj-tiny-kv.storage.wal.protocol :as wal]))

(defrecord MemoryStorage [state logger]
  Storage
  (load! [this]
    this)

  (put! [_this k v]
    (when logger
      (wal/write-put! logger k v))

    (swap! state assoc k {:exists? true :value v})
    {:status :ok})

  (get-value [_this k]
    (if-some [{:keys [value]} (get @state k)]
      {:status :ok :value value}
      {:status :not-found}))

  (delete! [_this k]
    (let [exists? (contains? @state k)]
      (when logger
        (wal/write-delete! logger k))
      (swap! state dissoc k)
      (if exists?
        {:status :ok}
        {:status :not-found})))

  (dump [_this]
    @state)

  (close! [_this]
    :ok))
