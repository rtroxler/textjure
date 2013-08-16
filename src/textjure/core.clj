(ns textjure.core)

;#### Global Vars
(def objects '(whiskey-bottle bucket frog chain))
(def object-locations (hash-map
                        'whiskey-bottle :living-room
                        'bucket :living-room
                        'chain :garden
                        'frog :garden))
(def location :living-room)

(def game-map {
           :living-room '("you are in the living room -" ((west door :garden)(upstairs stairway :attic)))
           :garden      '("you are in the garden -" ((east door :living-room)))
           :attic       '("you are in an attic -" ((downstairs stairway :living-room))) })


;##### Helper functions
(defn is-at? [obj loc obj-loc] 
  (= (obj obj-loc) loc))

(defmacro defspel [& rest] `(defmacro ~@rest))

(defspel walk [direction] `(walk-direction '~direction))


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


;##### ACTIONS
(defn look []
  (apply str (concat (describe-location location game-map)
          (describe-paths location game-map)
          (describe-floor location objects object-locations))))

(defn walk-direction [direction]
  (let [next (first (filter (fn [x] (= direction (first x)))
                            (first (rest (location game-map)))))]
    (cond next (do (def location (nth next 2)) (look))
          :else (println "you cannot go that way -"))))


;#### Game movement shit
(defn eval-input [input]
  (let [s (clojure.string/split input #"\s+")]
    ((resolve (symbol (first s))) (second s))))

(defn game []
  (look)
  (let [v (read-line)]
    (if-not (empty? v)
      (eval-input v)
      (println "I don't understand that command."))
    (recur)))

(defn -main 
  "I don't do a whole lot."
  []
  (println "Welcome to _____")
  (game))



