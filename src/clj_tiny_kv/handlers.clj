(ns clj-tiny-kv.handlers)

(defn get-handler [request]
  {:status 200
   :body ""})

(defn put-handler [request]
  {:status 201
   :body ""})

(defn delete-handler [request]
  {:status 200
   :body ""})

(defn not-found-handler [_]
  {:status 404
   :headers {"Content-Type" "application/json"}
   :body "{\"error\": \"Not Found\"}"})