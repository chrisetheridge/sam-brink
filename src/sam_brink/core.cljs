(ns sam-brink.core
  (:require
   [rum.core :as rum]
   [sam-brink.util :as util]
   [sam-brink.state :as state]
   [sam-brink.ui.routes :as routes]
   [sam-brink.routing :as routing]))

(enable-console-print!)

(defn ^:export start! []
  (prn "[core] starting")
  (routing/start-routes! routes/routes state/*state)
  (routing/refresh))
