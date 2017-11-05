(ns sam-brink.server
  (:require [clojure.string :as str]))

(defmacro p
  [& words]
  (str "<p>"
       (str/join " " words)
       "</p>"))
