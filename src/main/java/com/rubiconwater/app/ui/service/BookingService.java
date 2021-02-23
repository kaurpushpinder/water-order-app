package com.rubiconwater.app.ui.service;

import java.time.LocalDateTime;
import java.util.List;

import com.rubiconwater.app.ui.model.OrderDto;

public interface BookingService {
	
	/**
	 * create new order with given order details, 
	 * condition that requested time does not overlap existing orders
	 * @param orderDetails
	 * @return
	 */
	OrderDto createBooking(OrderDto order);
	
	/**
	 * Get all orders based on request parameters
	 * @param farmerId
	 * @param date
	 * @return
	 */
	List<OrderDto> getOrders(String farmerId, LocalDateTime date);

	
	/**
	 * Cancel an existing order,
	 * condition that Order is not yet started
	 * @param orderId
	 * @return
	 */
	void cancelOrder(String orderId);

	/**
	 * get orders by id
	 * @param orderId
	 * @return
	 */
	OrderDto getOrderById(String orderId);
}
