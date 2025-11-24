(ns clj-tiny-kv.middleware.exception
  (:require
   [reitit.ring.middleware.exception :as exception]
   [cheshire.core :as json]))

(defn json-error [status message data]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (json/encode {:error message
                       :details data})})

(def custom-handlers
  (merge
   exception/default-handlers

   {::bad-request
    (fn [e _req]
      (json-error 400 "Bad Request" (ex-data e)))

    ::exception/default
    (fn [e _]
      (json-error 500 "Internal Server Error"
                  {:exception (str (.getClass e))
                   :message (.getMessage e)}))

    ::exception/wrap
    (fn [handler e request]
      (println "ERROR:" (.getMessage e) "@" (:uri request))
      (handler e request))}))

(def exception-middleware
  (exception/create-exception-middleware custom-handlers))
