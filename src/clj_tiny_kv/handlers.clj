(ns clj-tiny-kv.handlers
  (:require
   [cheshire.core :as json]))

(defn make-response [status body]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (json/encode body)})

(defn get-handler [request]
  (let [storage (:storage request)
        key (get-in request [:path-params :key])]
    (try
      (let [result (.get-value storage key)
            status (:status result)]
        (case status
          :ok
          (make-response 200 {:status "ok"
                              :value (:value result)})
          :not-found
          (make-response 404 {:status "not-found"})))
      (catch Exception e
        (make-response 500 {:status "error"
                            :message (.getMessage e)})))))
(defn put-handler
  [request]
  (let [storage (:storage request)
        key (get-in request [:path-params :key])
        body (slurp (:body request))]
    (try
      (let [value (:value (json/decode body true))]
        (.put! storage key value)
        (make-response 201 {:status "created"
                            :key key
                            :value value}))
      (catch Exception e
        (make-response 500 {:status "error"
                            :message (.getMessage e)})))))

(defn delete-handler [request]
  (let [storage (:storage request)
        key (get-in request [:path-params :key])
        status (:status (.delete! storage key))]
    (case (status)
      :deleted (make-response 200 {:status "deleted"
                                   :key key})
      :not-found (make-response 404 {:status "not-found"}))))

(defn not-found-handler [_]
  (make-response 404 {:status "not-found"}))

(defn method-not-allowed-handler [_]
  (make-response 405 {:status "method-not-allowed"}))
