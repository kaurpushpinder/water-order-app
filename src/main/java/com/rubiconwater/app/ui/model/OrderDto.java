package com.rubiconwater.app.ui.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.rubiconwater.app.ui.data.OrderStatus;


public class OrderDto implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4098739032421717517L;

	private String orderId;
	
	private String farmerId;
	
	private LocalDateTime startDateTime;
	
	private int duration;
	
	private OrderStatus status;
	
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the farmerId
	 */
	public String getFarmerId() {
		return farmerId;
	}
	/**
	 * @param farmerId the farmerId to set
	 */
	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
	 * @return the startDateTime
	 */
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	/**
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	/**
	 * @return the status
	 */
	public OrderStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
