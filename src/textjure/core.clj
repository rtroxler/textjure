(ns textjure.core)

(def objects '(whiskey-bottle bucket frog chain))
(def object-locations (hash-map
                        'whiskey-bottle :living-room
                        'bucket :living-room
                        'chain :garden
                        'frog :garden))
(def location :living-room)

(defn is-at? [obj loc obj-loc] 
  (= (obj obj-loc) loc))

(def game-map {
           :living-room '("you are in the living room -" ((west door :garden)(upstairs stairway :attic)))
           :garden      '("you are in the garden -" ((east door :living-room)))
           :attic       '("you are in an attic -" ((downstairs stairway living-room))) })

;##### Describing things
(defn describe-location [location game-map]
  (println (first (location game-map))))

(defn describe-path [path]
  (let [obj (second path) dir (first path)]
    (println "there is a" obj "going" dir "from here -")))

(defn describe-paths [location game-map]
  (apply str (concat (map describe-path (last (get game-map location))))))

(defn describe-floor [loc objs obj-loc]
  (apply str (concat (map (fn [x]
                       (println "you see a" x "on the floor -"))
                     (filter (fn [x]
                               (is-at? x loc obj-loc)) objs)))))

(defn look []
  (apply str (concat (describe-location location game-map)
          (describe-paths location game-map)
          (describe-floor location objects object-locations))))

(defn -main 
  "I don't do a whole lot."
  []
  (println "Welcome to _____")
  (look))
