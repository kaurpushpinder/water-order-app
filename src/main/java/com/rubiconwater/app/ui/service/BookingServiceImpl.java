/**
 * 
 */
package com.rubiconwater.app.ui.service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubiconwater.app.ui.data.OrderDateTime;
import com.rubiconwater.app.ui.data.OrderDateTimeRepository;
import com.rubiconwater.app.ui.data.OrderEntity;
import com.rubiconwater.app.ui.data.OrderRepository;
import com.rubiconwater.app.ui.data.OrderStatus;
import com.rubiconwater.app.ui.exceptions.BookingProcessException;
import com.rubiconwater.app.ui.model.OrderDto;

/**
 * @author pushpinder
 *
 */
@Service
public class BookingServiceImpl implements BookingService {
	
	OrderRepository orderRepository;
	
	OrderDateTimeRepository orderDateTimeRepo;
	
	SchedueOrderEventsService scheduler;
	
	Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
	
	@Autowired
	public BookingServiceImpl(OrderRepository orderRepo, OrderDateTimeRepository orderDateTimeRepo, SchedueOrderEventsService scheduler) {
		this.orderRepository = orderRepo;
		this.orderDateTimeRepo = orderDateTimeRepo;
		this.scheduler = scheduler;
	}

	@Override
	public OrderDto createBooking(OrderDto order) {
		if (!overlapping(order) && order.getStartDateTime().isAfter(LocalDateTime.now())) {
			long maxRowId = orderRepository.getMaxRowId(); 
			order.setOrderId(maxRowId > 0 ? "order00" + (maxRowId + 1) : "order001" );
			order.setStatus(OrderStatus.REQUESTED);
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			
			OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
			orderRepository.save(orderEntity);
			
			OrderDateTime orderDateTime = createOrderDateTimeInstance(orderEntity);
			orderDateTimeRepo.save(orderDateTime);
			logger.info("Order " + orderEntity.getOrderId() + " created at " + LocalDateTime.now());
			OrderDto returnValue = modelMapper.map(orderEntity, OrderDto.class);
			return returnValue;			
		} else {
			return null;
		}
	}

	@Override
	public List<OrderDto> getOrders(String farmerId, LocalDateTime date) {
		ModelMapper modelMapper = new ModelMapper();
		LocalDateTime startDate = null, endDate = null;
		if (date != null) {
			startDate = date.withHour(0).withMinute(0).withSecond(0);
			endDate = startDate.plusDays(1);			
		}
		List<OrderEntity> ordersEntity = orderRepository.findOrders(farmerId, startDate, endDate);
		List<OrderDto> orders = new ArrayList<OrderDto>();
		Type listType = new TypeToken<List<OrderDto>>(){}.getType();
		orders = modelMapper.map(ordersEntity, listType);
		return orders;
	}
	
	@Override
	public void cancelOrder(String orderId) {
		OrderEntity order = orderRepository.findOrderById(orderId);
		if (order != null && order.getStatus().equals(OrderStatus.REQUESTED)) {
			orderRepository.updateStatus(orderId, OrderStatus.CANCELLED);
			logger.info("Order " + orderId + " cancelled at " + LocalDateTime.now());
			orderDateTimeRepo.removeEntity(orderId);
			scheduler.removeOrderEvents(orderId);
		} else {
			if (order == null) {
				throw new BookingProcessException("Order not available");				
			} else {
				throw new BookingProcessException("Order can not be cancelled");
			}
		}
	}
	
	/**
	 * Create date-time instance related to order.
	 * @param orderEntity
	 * @return
	 */
	private OrderDateTime createOrderDateTimeInstance(OrderEntity orderEntity) {
		OrderDateTime orderDateTime = new OrderDateTime();
		orderDateTime.setOrderId(orderEntity.getOrderId());
		
		orderDateTime.setStartDateTime(orderEntity.getStartDateTime());
		orderDateTime.setEndDateTime(orderEntity.getStartDateTime().plusMinutes(orderEntity.getDuration()));
		orderDateTime.setStartDate(orderEntity.getStartDateTime().withHour(0).withMinute(0).withSecond(0));
		
		scheduler.addOrderEventsToScheduler(orderDateTime.getOrderId(), orderDateTime.getStartDateTime(), orderDateTime.getEndDateTime());
		return orderDateTime;
	}
	
	/**
	 * check if requested order time does not overlap existing order time slots
	 * @param requestedOrder
	 * @return
	 */
	private boolean overlapping(OrderDto requestedOrder) {
		OrderDateTime tempEntry = new OrderDateTime();
		tempEntry.setStartDateTime(requestedOrder.getStartDateTime());
		tempEntry.setEndDateTime(requestedOrder.getStartDateTime().plusMinutes(requestedOrder.getDuration()));
		tempEntry.setStartDate(requestedOrder.getStartDateTime().withHour(0).withMinute(0).withSecond(0));
		orderDateTimeRepo.save(tempEntry);
		List<OrderDateTime> overlappingSlots = orderDateTimeRepo.findOverlappingSlots(tempEntry.getStartDateTime(), 
				tempEntry.getEndDateTime(), tempEntry.getStartDate(), tempEntry.getStartDate().plusDays(1));
		orderDateTimeRepo.delete(tempEntry);
		if (overlappingSlots.size() == 1 && overlappingSlots.get(0).getOrderId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public OrderDto getOrderById(String orderId) {
		ModelMapper modelMapper = new ModelMapper();
		
		OrderEntity ordersEntity = orderRepository.findOrderById(orderId);
		
		OrderDto orders = modelMapper.map(ordersEntity, OrderDto.class);
		return orders;
	}

}
