(ns clj-tiny-kv.routes
  (:require
   [clj-tiny-kv.handlers :as handlers]
   [reitit.ring :as ring]))

(def app
  (ring/ring-handler
   (ring/router
    [["/v1/key/:key" {:get    handlers/get-handler
                      :put    handlers/put-handler
                      :delete handlers/delete-handler}]])
   handlers/not-found-handler))