package org.lms.shared.controllers;

import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.lms.shared.models.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final ThreadLocal<String> exceptionMessageHolder = new ThreadLocal<>();

	@ExceptionHandler(HttpBadRequestException.class)
	public ResponseEntity<CommonResponse> handleBadRequestException(HttpBadRequestException e) {
		exceptionMessageHolder.set(e.getMessage());
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(HttpNotFoundException.class)
	public ResponseEntity<CommonResponse> handleNotFoundException(HttpNotFoundException e) {
		exceptionMessageHolder.set(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(404, null, e.getMessage()));
	}

	public static String getExceptionMessage() {
		return exceptionMessageHolder.get();
	}

	public static void clearExceptionMessage() {
		exceptionMessageHolder.remove();
	}
}
