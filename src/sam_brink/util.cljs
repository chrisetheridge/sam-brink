(ns sam-brink.util)

(defn static-image [path]
  (str "/img/" path))

(defn random-str [n]
  (->> (take n (cycle ["Lorem ipsum doelm mit ex zit muipet"]))
       (apply str)))
