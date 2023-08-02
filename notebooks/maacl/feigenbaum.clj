;; # Feigenbaum

^{:nextjournal.clerk/toc true}
(ns maacl.feigenbaum
    (:require [mentat.clerk-utils.show :refer [show-sci]]
              [nextjournal.clerk :as clerk]))

(clerk/eval-cljs
 '(do (require '[reagent.core :as reagent])
      (require '[mathbox.core :as mathbox])
      (require '[mathbox.primitives :as mb])))

(show-sci
 
 (def ^{:doc "test data"}
    data
    [[0.5 0.5]
     [0.5 1.0]])
 
 (defn f [p x] (* p x (- 1 x)))
 
 (defn fixps [r]
   (->> (iterate (partial f r) (rand))
        (drop 300)
        (take 100)
        set
        (map (fn [x] (vector r x))))) 
 
 (def tree (mapcat fixps (range 2.5 4.0 0.005)))

 (def w (count tree))

 (defn Feigenbaum [] 
   [:<>
    [mb/Array {:width w :channels 2 :data tree}]
    [mb/Point {:color "#3090FF" :size 1}]])

  (defn scale
    ([axis-no] (scale axis-no [0 0 0 0]))
    ([axis-no origin]
    [:<>
     [mb/Scale  {:divide 10 :axis axis-no :origin origin}]
     [mb/Ticks  {:width 5 :size 15 :color "black"}]
     [mb/Format {:digits 2 :weight "bold"}]
     [mb/Label  {:color "red" :zIndex 1}]])))  

^{::clerk/width :wide}
(show-sci
 [mathbox/MathBox
  {:container {:style {:height "800px" :width "100%"}}
   :focus 3}
  [mb/Camera {:position [0 0 3]
              :proxy true}]
  [mb/Cartesian
   {:range [[2.5 4] [0 1]]}

   [mb/Axis {:axis 1 :width 2 :color "red"}]
   [mb/Axis {:axis 2 :width 2 :color "black" :origin [2.5 0 0 0]}]
   [mb/Grid {:width 1 :divideX 10 :divideY 10}]
    
   [scale 1] 
   [scale 2 [2.5 0 0 0]]

   [Feigenbaum]]])