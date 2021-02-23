package com.rubiconwater.app.ui.model;

import org.springframework.http.HttpStatus;

public class SuccessMessage {	
	
	public SuccessMessage() {}
	
	public SuccessMessage(String message, HttpStatus status) {
		this.message = message;
		this.setStatus(status);
	}
	
	private String message;
	
	private HttpStatus status;
	
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
