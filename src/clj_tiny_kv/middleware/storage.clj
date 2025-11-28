(ns clj-tiny-kv.middleware.storage)

(defn wrap-storage [handler storage]
  (fn [request]
    (handler (assoc request :storage storage))))
