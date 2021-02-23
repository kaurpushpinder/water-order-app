#Event logging enabled on console log, and also available in application.log file in app path

#Basic operations

/api/orders?farmerId=? (GET)	Returns all orders based on farmerId

/api/orders/{id} (GET)	Return order based on id

/api/orders?farmerId=?(GET)	Return orders based on farmerId, and/or date

/api/orders (POST)	Creates an order with given order details

/api/orders (DELETE)	Cancels an order

