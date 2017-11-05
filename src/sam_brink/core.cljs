(ns sam-brink.core
  (:require
   [sam-brink.routing :as routing]
   [sam-brink.state :as state]
   [sam-brink.ui.routes :as routes]))

(enable-console-print!)

(defn ^:export start! []
  (prn "[core] starting")
  (routing/start-routes! routes/routes state/*state)
  (routing/refresh))
