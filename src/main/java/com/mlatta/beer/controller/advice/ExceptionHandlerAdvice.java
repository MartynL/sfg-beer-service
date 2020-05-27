package com.mlatta.beer.controller.advice;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException e) {
		List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
		
		e.getConstraintViolations().forEach(err -> errors.add(err.toString()));
		
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity<List<ObjectError>> bindErrorHandler(BindException e) {
		return ResponseEntity.badRequest().body(e.getAllErrors());
	}
}
