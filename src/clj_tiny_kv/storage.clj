(ns clj-tiny-kv.storage)

(defonce store (atom {}))

(defn kv-put! [k v]
  (swap! store assoc k v)
  {:status :ok})

(defn kv-get [k]
  (get @store k))

(defn kv-delete! [k]
  (swap! store dissoc k)
  {:status :ok})

(defn kv-dump! []
  @store)
