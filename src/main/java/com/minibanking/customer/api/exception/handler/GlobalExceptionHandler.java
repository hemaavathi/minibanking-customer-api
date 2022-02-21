package com.minibanking.customer.api.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.minibanking.customer.api.exception.CustomerException;
import com.minibanking.customer.api.model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<Map<String,ErrorResponse>> handleCustomerException(CustomerException ce) {
		logger.error(ce.getMessage());
		ErrorResponse error = new ErrorResponse(ce.getCode(), ce.getMessage());
		Map<String,ErrorResponse> eResponse = new HashMap<>();
		eResponse.put("error", error);
		ResponseEntity<Map<String,ErrorResponse>> entity = new ResponseEntity<>(eResponse,ce.getHttpStatus());
		return entity;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ce) {
		logger.error("MethodArgumentNotValidException occurred");
		ErrorResponse error = new ErrorResponse(2500, ce.getFieldError().getDefaultMessage());
		Map<String,ErrorResponse> eResponse = new HashMap<>();
		eResponse.put("error", error);
		ResponseEntity<Map<String,ErrorResponse>> entity = new ResponseEntity<>(eResponse, HttpStatus.BAD_REQUEST);
		return entity;
	}
	
	
}
