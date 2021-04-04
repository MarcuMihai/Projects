(define (domain cakeshopdomain)
	(:requirements :strips :action-costs)
	(:predicates (cakeshopIsOpen)
               		(Client ?x)
			(hasClient)
               		(cookies) 
               		(donuts) 
			(cheesecake) 
			(lavacake) 
		 	(applepie) 
			(eclair) 
			(hasSugar)
			(hasButter)
			(hasFlour)
			(hasChocolate)
			(hasMilk)
			(hasEggs)
			(hasBakingPowder)
			(hasSalt)
		 	(hasCheeseCream)
			(hasStrawberries)
			(hasVanillaEssence)
			(hasApples)
			(ordersCookies ?x)
               		(ordersDonuts ?x)
               		(ordersCheesecake ?x)
			(ordersLavacake ?x)
			(ordersApplepie ?x)
			(ordersEclair ?x)
			(cookiesOrderComplete ?x)
              	 	(donutsOrderComplete ?x)
              		(cheesecakeOrderComplete ?x)
			(lavacakeOrderComplete ?x)
			(applepieOrderComplete ?x)
			(eclairOrderComplete ?x)
			(withDelivery ?x)
			(productsSent ?x)
			(clientPaid ?x))
	
	(:functions (total-cost))
	
	(:action openCakeshop
           :parameters ()
           :precondition (not (cakeshopIsOpen))
           :effect (cakeshopIsOpen)) 
		   
	(:action newClient
           :parameters (?x)
           :precondition (and(not (Client ?x)) (cakeshopIsOpen) (not (hasClient)))
           :effect ( and(Client ?x) (hasClient)))
		   
	(:action makeCookies
           :parameters ()
           :precondition (and(not(cookies)) (hasButter) (hasSugar) (hasFlour)
		   (hasBakingPowder) (hasSalt) (hasChocolate) (hasMilk))
           :effect (and(cookies) (not(hasButter)) (not(hasSugar)) (not(hasFlour))
		   (not(hasBakingPowder)) (not(hasSalt)) (not(hasChocolate)) (not(hasMilk))))
		   
	(:action makeDonuts
           :parameters ()
           :precondition (and(not(donuts)) (hasButter) (hasSugar) (hasFlour)
		   (hasBakingPowder) (hasSalt) (hasChocolate) (hasMilk) (hasEggs))
           :effect (and(donuts) (not(hasButter)) (not(hasSugar)) (not(hasFlour))
		   (not(hasBakingPowder)) (not(hasSalt)) (not(hasChocolate))
		   (not(hasMilk)) (not(hasEggs))))
		   
	(:action makeCheesecake
           :parameters ()
           :precondition (and(not(cheesecake)) (hasButter) (hasSugar) (hasEggs)
		   (hasBakingPowder) (hasSalt) (hasStrawberries) (hasCheeseCream))
           :effect (and(cheesecake) (not(hasButter)) (not(hasSugar)) (not(hasEggs))
		   (not(hasBakingPowder)) (not(hasSalt)) (not(hasStrawberries)) (not(hasCheeseCream))))
           
	(:action makeLavacake
           :parameters ()
           :precondition (and(not(lavacake)) (hasButter) (hasSugar) (hasEggs)
		   (hasFlour) (hasBakingPowder) (hasSalt) (hasChocolate) (hasMilk))
           :effect (and(lavacake) (not(hasButter)) (not(hasSugar)) (not(hasEggs))
		   (not(hasFlour)) (not(hasBakingPowder)) (not(hasSalt)) (not(hasChocolate)) (not(hasMilk))))
	
	(:action makeApplepie
           :parameters ()
           :precondition (and(not(applepie)) (hasButter) (hasSugar) (hasFlour)
		   (hasBakingPowder) (hasSalt) (hasEggs) (hasApples))
           :effect (and(applepie) (not(hasButter)) (not(hasSugar)) (not(hasEggs))
		   (not(hasFlour)) (not(hasBakingPowder)) (not(hasSalt)) (not(hasApples))))
	
	(:action makeEclair
           :parameters ()
           :precondition (and(not(eclair)) (hasButter) (hasSugar) (hasFlour) 
		   (hasBakingPowder) (hasSalt) (hasChocolate) (hasEggs) (hasMilk) (hasVanillaEssence))
           :effect (and(eclair) (not(hasButter)) (not(hasSugar)) (not(hasEggs)) 
		   (not(hasFlour)) (not(hasBakingPowder)) (not(hasSalt)) (not(hasChocolate)) 
		   (not(hasMilk)) (not(hasVanillaEssence))))
		   
	(:action buyButter
	   :parameters ()
           :precondition (not(hasButter))
           :effect (hasButter))
	
	(:action buyMilk
	   :parameters ()
           :precondition (not(hasMilk))
           :effect (hasMilk))
		   
	(:action buyFlour
	   :parameters ()
           :precondition (not(hasFlour))
           :effect (hasFlour))
	
	(:action buySugar
	   :parameters ()
           :precondition (not(hasSugar))
           :effect (hasSugar))
	
	(:action buyCheesecream
	   :parameters ()
           :precondition (not(hasCheesecream))
           :effect (hasCheesecream))
	
	(:action buyChocolate
	   :parameters ()
           :precondition (not(hasChocolate))
           :effect (hasChocolate))
	
	(:action buyVanillaEssence
	   :parameters ()
           :precondition (not(hasVanillaEssence))
           :effect (hasVanillaEssence))
	
	(:action buyBakingPowder
	   :parameters ()
           :precondition (not(hasBakingPowder))
           :effect (hasBakingPowder))
	
	(:action buySalt
	   :parameters ()
           :precondition (not(hasSalt))
           :effect (hasSalt))
		   
	(:action buyEggs
	   :parameters ()
           :precondition (not(hasEggs))
           :effect (hasEggs))
	
	(:action buyApples
	   :parameters ()
           :precondition (not(hasApples))
           :effect (hasApples))
	
	(:action buyStrawberries
	   :parameters ()
           :precondition (not(hasStrawberries))
           :effect (hasStrawberries))
	
	(:action orderedCookies
           :parameters (?x)
           :precondition (and (Client ?x) (ordersCookies ?x) (cookies))
           :effect (and (cookiesOrderComplete ?x) (not(cookies)) 
		   (not(ordersCookies ?x))  (increase (total-cost) 1)))
		  
	(:action orderedDonuts
           :parameters (?x)
           :precondition (and (Client ?x) (ordersDonuts ?x) (donuts))
           :effect ( and(donutsOrderComplete ?x) (not(donuts)) 
		   (not(ordersDonuts ?x))  (increase (total-cost) 1)))
	
	(:action orderedCheesecake
           :parameters (?x)
           :precondition (and (Client ?x) (ordersCheesecake ?x) (cheesecake))
           :effect ( and(cheesecakeOrderComplete ?x) (not(cheesecake)) 
		   (not(ordersCheesecake ?x))  (increase (total-cost) 2)))
		   
	(:action orderedLavacake
           :parameters (?x)
           :precondition (and (Client ?x) (ordersLavacake ?x) (lavacake))
           :effect ( and(lavacakeOrderComplete ?x) (not(lavacake)) 
		   (not(ordersLavacake ?x))  (increase (total-cost) 3)))
		   
	(:action orderedApplepie
           :parameters (?x)
           :precondition (and (Client ?x) (ordersApplepie ?x) (applepie))
           :effect ( and(applepieOrderComplete ?x) (not(applepie)) 
		   (not(ordersApplepie ?x))  (increase (total-cost) 1)))
		   
	(:action orderedEclair
           :parameters (?x)
           :precondition (and (Client ?x) (ordersEclair ?x) (eclair))
           :effect ( and(eclairOrderComplete ?x) (not(eclair)) 
		   (not(ordersEclair ?x))  (increase (total-cost) 2)))
                 
	(:action sendDelivery
	   :parameters (?x)
           :precondition (and (Client ?x) (withDelivery ?x) ( or(cookiesOrderComplete ?x) 
		   (applepieOrderComplete ?x) (cheesecakeOrderComplete ?x) 
		   (donutsOrderComplete ?x) (eclairOrderComplete ?x) (lavacakeOrderComplete ?x))
           (not(or(ordersApplepie ?x) (ordersCheesecake ?x) (ordersCookies ?x) 
		   (ordersDonuts ?x) (ordersEclair ?x) (ordersLavacake ?x))))
           :effect (productsSent ?x))
		   
	(:action clientPicksProducts
	   :parameters (?x)
           :precondition (and (Client ?x) ( not(withDelivery ?x)) ( or(cookiesOrderComplete ?x) 
		   (applepieOrderComplete ?x) (cheesecakeOrderComplete ?x) (donutsOrderComplete ?x) 
		   (eclairOrderComplete ?x) (lavacakeOrderComplete ?x))
           (not(or(ordersApplepie ?x) (ordersCheesecake ?x) (ordersCookies ?x) 
		   (ordersDonuts ?x) (ordersEclair ?x) (ordersLavacake ?x))))
           :effect (productsSent ?x))
		
	(:action clientPays
	   :parameters (?x)
           :precondition (and (Client ?x) (productsSent ?x))
           :effect ( and(clientPaid ?x) (not(hasClient)) (not (Client ?x))))
)