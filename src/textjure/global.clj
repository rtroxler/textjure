(ns textjure.global)

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
