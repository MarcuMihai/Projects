(define (problem cakeshopprob3)
  (:domain cakeshopdomain)
  (:objects a b c)
  (:init (ordersCheesecake a) (ordersCookies a) (withDelivery a) 
  (ordersLavacake b) (ordersCookies b) (withDelivery b) 
  (ordersApplepie c) (ordersEclair c)
  (=(total-cost) 0))
  (:goal (and (cheesecakeOrderComplete a) (cookiesOrderComplete a) (clientPaid a) 
  (lavacakeOrderComplete b) (cookiesOrderComplete b) (clientPaid b) 
  (applepieOrderComplete c) (eclairOrderComplete c) (clientPaid c)))
  (:metric minimize(total-cost)))