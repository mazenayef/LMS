package org.lms.shared.aspects;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.lms.shared.annotations.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatusCodeAspect {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Around("@annotation(statusCode)")
	public Object getStatusCode(ProceedingJoinPoint joinPoint, StatusCode statusCode) throws Throwable {
		HttpStatus statusCodeValue = statusCode.value();

		response.setStatus(statusCodeValue.value());
		return joinPoint.proceed();
	}
}
