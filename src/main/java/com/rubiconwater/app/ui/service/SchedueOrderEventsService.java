package com.rubiconwater.app.ui.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.rubiconwater.app.ui.data.OrderDateTimeRepository;
import com.rubiconwater.app.ui.data.OrderRepository;
import com.rubiconwater.app.ui.data.OrderStatus;

@Service
public class SchedueOrderEventsService {

	// Task Scheduler
	TaskScheduler scheduler;
	
	Logger logger = LoggerFactory.getLogger(SchedueOrderEventsService.class);
	
	// A map for keeping scheduled tasks
	Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();
	
	OrderRepository orderRepository;
	
	OrderDateTimeRepository orderDateTimeRepo;
	
	@Autowired
	public SchedueOrderEventsService(TaskScheduler scheduler, OrderRepository orderRepo, OrderDateTimeRepository orderDateTimeRepo) {
		this.scheduler = scheduler;
		this.orderRepository = orderRepo;
		this.orderDateTimeRepo = orderDateTimeRepo;
	}
	
	/**
	 * add tasks to Schedulers for starting and ending an order
	 * @param orderId
	 * @param startDateTime
	 * @param endDateTime
	 */
	public void addOrderEventsToScheduler(String orderId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		addTaskToScheduler(orderId + "_start", () -> {startOrder(orderId);}, generateCronExpression(startDateTime));
		addTaskToScheduler(orderId + "_end", () -> {endOrder(orderId);}, generateCronExpression(endDateTime));
	}
	
	/**
	 * remove order events from schedulers (in case order is cancelled)
	 * @param orderId
	 */
	public void removeOrderEvents(String orderId) {
		removeTaskFromScheduler(orderId + "_start");
		removeTaskFromScheduler(orderId + "_end");
	}
	
	/**
	 * Schedule task based on cron-expression for given eventId
	 * @param eventId
	 * @param task
	 * @param cronExpression
	 */
	private void addTaskToScheduler(String eventId, Runnable task, String cronExpression) {
		ScheduledFuture<?> scheduledTask = scheduler.schedule(task, new CronTrigger(cronExpression, TimeZone.getTimeZone(TimeZone.getDefault().getID())));
		jobsMap.put(eventId, scheduledTask);
	}
	
	// Remove scheduled task 
	private void removeTaskFromScheduler(String eventId) {
		ScheduledFuture<?> scheduledTask = jobsMap.get(eventId);
		if(scheduledTask != null) {
			scheduledTask.cancel(true);
			jobsMap.put(eventId, null);
		}
	}
	
	/**
	 * Generate a CRON expression based on event time.
	 *
	 * @param dateTime object
	 * @return a CRON Formatted String.
	 */
	private static String generateCronExpression(LocalDateTime eventDateTime) {
		
		return String.format("0 %1$s %2$s %3$s %4$s *", eventDateTime.getMinute(),
				eventDateTime.getHour(), eventDateTime.getDayOfMonth(),
				eventDateTime.getMonthValue());
	}
	
	/**
	 * Start Order by changing the status to 'In-progress'
	 * @param orderId
	 */
	private void startOrder(String orderId) {
		orderRepository.updateStatus(orderId, OrderStatus.IN_PROGESS);
		logger.info("Order " + orderId + " started at " + LocalDateTime.now());
	}
	
	/**
	 * Start Order by changing the status to 'Delivered'
	 * @param orderId
	 */
	private void endOrder(String orderId) {
		orderRepository.updateStatus(orderId, OrderStatus.DELIVERED);
		orderDateTimeRepo.removeEntity(orderId);
		logger.info("Order " + orderId + " completed at " + LocalDateTime.now());
	}
	// A context refresh event listener
	@EventListener({ ContextRefreshedEvent.class })
	void contextRefreshedEvent() {
		// Get all tasks from DB and reschedule them in case of context restarted
	}
}