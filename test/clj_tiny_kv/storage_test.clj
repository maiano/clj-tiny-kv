(ns clj-tiny-kv.storage-test
  (:require [clojure.test :as t]
            [clj-tiny-kv.storage :as storage]))

(t/deftest basic-tests
  (t/testing "put and get"
    (reset! storage/store {})
    (storage/kv-put "a" 1)
    (t/is (= 1 (storage/kv-get "a"))))

  (t/testing "delete"
    (storage/kv-put "b" 2)
    (storage/kv-delete "b")
    (t/is (nil? (storage/kv-get "b")))))
