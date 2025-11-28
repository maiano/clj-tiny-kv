(ns clj-tiny-kv.storage.core
  (:require [clj-tiny-kv.storage.memory.core :refer [->MemoryStorage]]))

(defn create-memory-storage []
  (->MemoryStorage (atom {})))
