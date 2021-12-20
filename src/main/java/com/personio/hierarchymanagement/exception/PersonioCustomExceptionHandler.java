package com.personio.hierarchymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PersonioCustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(PersonioCustomException.class)
	public final ResponseEntity<?> handlePersonioCustomExceptions(PersonioCustomException e){
	
		return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.BAD_REQUEST);
	}
}
