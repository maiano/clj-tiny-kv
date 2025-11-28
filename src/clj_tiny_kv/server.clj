(ns clj-tiny-kv.server
  (:require
   [clj-tiny-kv.core :refer [app]]
   [ring.adapter.jetty :as jetty])
  (:gen-class))

(defonce server (atom nil))

(defn start-server [port]
  (when-not @server
    (reset! server
            (jetty/run-jetty (app) {:port port :join? false}))
    (println (str "Server started on port " port)))
  @server)

(defn stop-server []
  (when-let [s @server]
    (.stop s)
    (reset! server nil)
    (println "Server stopped")))

(defn -main [& _]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "4000"))]
    (start-server port)))