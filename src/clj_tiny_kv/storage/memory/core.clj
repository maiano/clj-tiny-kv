(ns clj-tiny-kv.storage.memory.core
  (:require
   [clj-tiny-kv.storage.protocol :refer [Storage]]
   [clj-tiny-kv.storage.wal.protocol :as wal-proto]
   [clj-tiny-kv.storage.wal.core :as wal]))

(defrecord MemoryStorage [state logger]
  Storage
  (load! [this]
    (when logger
      (doseq [e (wal/replay logger)]
        (case (:event-type e)
          :put    (swap! state assoc (:key e)
                         {:exists? true :value (:value e)})
          :delete (swap! state dissoc (:key e)))))
    this)

  (put! [_this k v]
    (when logger
      (wal-proto/write-put! logger k v))
    (swap! state assoc k {:exists? true :value v})
    {:status :ok})

  (get-value [_this k]
    (if-some [{:keys [value]} (get @state k)]
      {:status :ok :value value}
      {:status :not-found}))

  (delete! [_this k]
    (let [exists? (contains? @state k)]
      (when logger
        (wal-proto/write-delete! logger k))
      (swap! state dissoc k)
      (if exists?
        {:status :ok}
        {:status :not-found})))

  (dump [_this]
    @state)

  (close! [_this]
    :ok))
