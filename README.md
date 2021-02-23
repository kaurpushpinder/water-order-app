
#Endpoint	What it does

/api/orders?farmerId=? (GET)	Returns all orders based on farmerId

/api/orders/{id} (GET)	Return order based on id

/api/orders?farmerId=?&date=?(GET)	Return orders based on farmerId, and/or date

/api/orders (POST)	Creates an order with given order details

/api/orders (DELETE)	Cancels an order
