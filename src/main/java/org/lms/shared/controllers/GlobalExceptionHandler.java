package org.lms.shared.controllers;

import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.shared.exceptions.HttpForbiddenException;
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
		System.out.println(e.getMessage());
		exceptionMessageHolder.set(e.getMessage());
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(HttpNotFoundException.class)
	public ResponseEntity<CommonResponse> handleNotFoundException(HttpNotFoundException e) {
		System.out.println(e.getMessage());
		exceptionMessageHolder.set(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@ExceptionHandler(HttpForbiddenException.class)
	public ResponseEntity<CommonResponse> handleForbiddenException(HttpNotFoundException e) {
		System.out.println(e.getMessage());
		exceptionMessageHolder.set(e.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}


	public static String getExceptionMessage() {
		return exceptionMessageHolder.get();
	}

	public static void clearExceptionMessage() {
		exceptionMessageHolder.remove();
	}
}
