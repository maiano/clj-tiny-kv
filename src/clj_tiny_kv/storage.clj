(ns clj-tiny-kv.storage)

(defonce store (atom {}))

(defn kv-put! [k v]
  (swap! store assoc k {:exists? true :value v})
  {:status :ok})

(defn kv-get [k]
  (if-let [v (get @store k)]
    {:status :ok :value (:value v)}
    {:status :not-found}))

(defn kv-delete! [k]
  (let [exists? (contains? @store k)]
    (swap! store dissoc k)
    (if exists?
      {:status :deleted}
      {:status :not-found})))

(defn kv-dump []
  @store)
