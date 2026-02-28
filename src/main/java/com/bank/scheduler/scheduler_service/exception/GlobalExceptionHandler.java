package com.bank.scheduler.scheduler_service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import com.bank.scheduler.scheduler_service.dto.ErrorResponseDto;

@RestControllerAdvice // @Controlleradvice + @ResponseBody
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {

		logger.warn("Business validation failed: {}", ex.getMessage());

		ErrorResponseDto errorResponse = new ErrorResponseDto("VALIDATION_ERROR", ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {

		logger.error("Unhandled exception occurred", ex);

		ErrorResponseDto errorResponse = new ErrorResponseDto("INTERNAL_SERVER_ERROR",
				"Unexpected error occurred. Please contact support.");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
	
	@ExceptionHandler(ResourceAccessException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceAccessException(ResourceAccessException ex) {

		logger.error("External service is not reachable: {}", ex.getMessage());

		ErrorResponseDto errorResponse = new ErrorResponseDto("SERVICE_UNAVAILABLE","Customer Management Service is currently unavailable. Please try again later.");

		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);

	}

}
