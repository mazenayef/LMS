package org.lms.shared.aspects;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.lms.shared.annotations.ResponseStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseStatusMessageAspect {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Around("@annotation(responseStatusMessage)")
	public Object getStatusMessage(ProceedingJoinPoint joinPoint, ResponseStatusMessage responseStatusMessage) throws Throwable {
		String statusMessageValue = responseStatusMessage.value();

		request.setAttribute("statusMessage", statusMessageValue);
		return joinPoint.proceed();
	}
}
