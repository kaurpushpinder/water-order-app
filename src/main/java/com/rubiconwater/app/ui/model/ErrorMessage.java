package com.rubiconwater.app.ui.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorMessage {	
	
	public ErrorMessage() {}
	
	public ErrorMessage(LocalDateTime timestamp, String message, HttpStatus status) {
		this.timestamp = timestamp;
		this.message = message;
		this.setStatus(status);
	}
	
	private LocalDateTime timestamp;
	
	private String message;
	
	private HttpStatus status;
	
	/**
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
}
