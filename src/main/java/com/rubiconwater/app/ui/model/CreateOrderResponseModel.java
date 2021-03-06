package com.rubiconwater.app.ui.model;

import java.time.LocalDateTime;

public class CreateOrderResponseModel {
	
	private String farmerId;

	private LocalDateTime startDateTime;
	
	private int duration;
	
	private String orderId;
	
	

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
}
