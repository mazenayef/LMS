package org.lms.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class HttpForbiddenException extends RuntimeException {
	public HttpForbiddenException(String message) {
		super(message);
	}
}
