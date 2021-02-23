/**
 * 
 */
package com.rubiconwater.app.ui.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rubiconwater.app.ui.exceptions.BookingProcessException;
import com.rubiconwater.app.ui.model.CreateOrderRequestModel;
import com.rubiconwater.app.ui.model.CreateOrderResponseModel;
import com.rubiconwater.app.ui.model.OrderDto;
import com.rubiconwater.app.ui.model.SuccessMessage;
import com.rubiconwater.app.ui.service.BookingService;

/**
 * @author pushpinder
 *
 */
@RestController
@RequestMapping("api/orders") //http://localhost:8080/api/booking
public class WaterOrderController {
	
	@Autowired
	BookingService bookingService;
	
	@GetMapping
	/**
	 * Get all orders based on request parameters
	 * @param farmerId - farmerId associated with orders
	 * @param date - date of orders
	 * @return
	 */
	public ResponseEntity<List<OrderDto>> getOrders(@RequestParam String farmerId,
			@RequestParam(required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
		List<OrderDto> orders = bookingService.getOrders(farmerId, date);
		return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.OK);
	}
	
	@GetMapping(path="/{orderId}")
	/**
	 * Get order based on order id
	 * @param orderId
	 * @return
	 */
	public ResponseEntity<OrderDto> getOrdersByOrderId(@PathVariable String orderId) {
		OrderDto order = bookingService.getOrderById(orderId);
		return new ResponseEntity<OrderDto>(order, HttpStatus.OK);
	}
	
	@PostMapping
	/**
	 * create new order with given order details consisting of {farmerId, startDateTime, duration in minutes), 
	 * condition that requested time doesnot overlap existing orders
	 * @param orderDetails consisting of {farmerId, startDateTime, duration in minutes)
	 * @return
	 */
	public ResponseEntity<CreateOrderResponseModel> createOrder(@RequestBody CreateOrderRequestModel orderDetails) {
		ModelMapper modelMapper = new ModelMapper();
		OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
		if (orderDto.getStartDateTime().isBefore(LocalDateTime.now())) {
			throw new BookingProcessException("Booking for past time not allowed");
		} else {
			OrderDto createdOrder = bookingService.createBooking(orderDto);
			if (createdOrder != null) {
				CreateOrderResponseModel returnValue = modelMapper.map(createdOrder, CreateOrderResponseModel.class);
				return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);				
			} else {
				throw new BookingProcessException("Requested time not available");
			}			
		}
	}
	
	
	@DeleteMapping("{orderId}")
	/**
	 * Cancel an existing order,
	 * condition that Order is not yet started
	 * @param orderId - orderId of the order
	 * @return
	 */
	public ResponseEntity<Object> cancelOrder(@PathVariable String orderId) {
		bookingService.cancelOrder(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Order Cancelled", HttpStatus.OK));
	}

}
