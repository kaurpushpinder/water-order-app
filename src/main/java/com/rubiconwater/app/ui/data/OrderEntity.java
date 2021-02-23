package com.rubiconwater.app.ui.data;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author pushpinder
 *
 */
//mark class as an Entity   
@Entity
//defining class name as Table name  
@Table 
public class OrderEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5463528715824301964L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false, unique=true)
	private String orderId;
	
	@Column(nullable=false, length=20)
	private String farmerId;
	
	@Column(nullable=false)
	private LocalDateTime startDateTime;
	
	@Column(nullable=false)
	private int duration;
	
	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.REQUESTED;
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
	 * 
	 * @return order status
	 */
	public OrderStatus getStatus() {
		return status;
	}
	
	/**
	 * 
	 * @param status
	 */
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
