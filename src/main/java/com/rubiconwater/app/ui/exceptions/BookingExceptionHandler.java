package com.rubiconwater.app.ui.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rubiconwater.app.ui.model.ErrorMessage;

/**
 * 
 * @author pushpinder
 *
 */
@ControllerAdvice
public class BookingExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		
		String errorMsgDesc = ex.getLocalizedMessage();
		if (errorMsgDesc == null) {
			errorMsgDesc = ex.getMessage();
		}
		
		ErrorMessage errorMsg = new ErrorMessage(LocalDateTime.now(), errorMsgDesc, HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<>(errorMsg, new HttpHeaders(), errorMsg.getStatus());
	}
	
	@ExceptionHandler(value = BookingProcessException.class)
	public ResponseEntity<Object> handleBookingNotAvailableExc(BookingProcessException ex, WebRequest request) {
		String errorMsgDesc = ex.getLocalizedMessage();
		if (errorMsgDesc == null) {
			errorMsgDesc = ex.getMessage();
		}
		
		ErrorMessage errorMsg = new ErrorMessage(LocalDateTime.now(), errorMsgDesc, HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<>(errorMsg, new HttpHeaders(), errorMsg.getStatus());
	}
}
