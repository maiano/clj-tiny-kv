(ns clj-tiny-kv.routes
  (:require
   [clj-tiny-kv.handlers :as handlers]
   [reitit.ring :as ring]
   [clj-tiny-kv.middleware.exception :as ex]))

(def router
  (ring/router
   [["/v1/key/:key"
     {:get    handlers/get-handler
      :put    handlers/put-handler
      :delete handlers/delete-handler}]]

   {:data {:middleware [ex/exception-middleware]}}))

(def app
  (ring/ring-handler
   router

   (ring/create-default-handler
    {:not-found handlers/not-found-handler
     :method-not-allowed handlers/method-not-allowed-handler})))
