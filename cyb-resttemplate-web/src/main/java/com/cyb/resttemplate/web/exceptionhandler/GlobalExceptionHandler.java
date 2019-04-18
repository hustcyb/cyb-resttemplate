package com.cyb.resttemplate.web.exceptionhandler;

import javax.ws.rs.WebApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(WebApplicationException.class)
	public ResponseEntity<?> handleWebApplicationException(WebApplicationException webApplicationException) {
		int status = webApplicationException.getResponse().getStatus();
		String message = webApplicationException.getMessage();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(message, HttpStatus.resolve(status));
		
		return responseEntity;		
	}
}
