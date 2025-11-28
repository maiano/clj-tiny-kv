(ns clj-tiny-kv.core
  (:require
   [clj-tiny-kv.routes :refer [router default-handler]]
   [clj-tiny-kv.middleware.storage :refer [wrap-storage]]
   [clj-tiny-kv.storage.core :refer [create-memory-storage]]
   [reitit.ring :as ring]))

(defn app []
  (let [storage (create-memory-storage)]
    (-> (ring/ring-handler router default-handler)
        (wrap-storage storage))))
