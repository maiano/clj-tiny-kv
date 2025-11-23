(ns clj-tiny-kv.storage)

(defonce store (atom {}))

(defn kv-put! [k v]
  (swap! store assoc k v)
  {:status :ok})

(defn kv-get [k]
  (let [v (get @store k)]
    (if (nil? v)
      {:status :not-found}
      {:status :ok
       :value  v})))

(defn kv-delete! [k]
  (let [exists? (contains? @store k)]
    (swap! store dissoc k)
    (if exists?
      {:status :deleted}
      {:status :not-found})))

(defn kv-dump []
  @store)
