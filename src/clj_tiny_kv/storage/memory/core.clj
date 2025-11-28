(ns clj-tiny-kv.storage.memory.core
  (:require
   [clj-tiny-kv.storage.protocol :refer [Storage]]))

(defrecord MemoryStorage [state]
  Storage
  (load! [this]
    this)

  (put! [_this k v]
    (swap! state assoc k {:exists? true :value v})
    {:status :ok})

  (get-value [_this k]
    (if-let [v (get @state k)]
      {:status :ok :value (:value v)}
      {:status :not-found}))

  (delete! [_this k]
    (let [exists? (contains? @state k)]
      (swap! state dissoc k)
      (if exists?
        {:status :ok}
        {:status :not-found})))

  (dump [_this]
    @state)

  (close! [_this]
    :ok))
