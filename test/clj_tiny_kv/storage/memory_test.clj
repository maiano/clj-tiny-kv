(ns clj-tiny-kv.storage.memory-test
  (:require [clojure.test :as t]
            [clj-tiny-kv.storage.core :refer [create-memory-storage]]
            [clj-tiny-kv.storage.protocol :as proto]))

(t/deftest basic-tests
  (let [st (create-memory-storage)]
    (t/testing "put and get"
      (proto/put! st "a" 1)
      (t/is (= {:status :ok :value 1}
               (proto/get-value st "a"))))

    (t/testing "delete"
      (proto/put! st "b" 2)
      (proto/delete! st "b")
      (t/is (= {:status :not-found}
               (proto/get-value st "b"))))))
