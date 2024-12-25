package org.lms.shared.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lms.shared.annotations.ExcludeFromCommonResponse;
import org.lms.shared.controllers.GlobalExceptionHandler;
import org.lms.shared.models.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		if (returnType.getContainingClass().isAnnotationPresent(ExcludeFromCommonResponse.class) ||
				returnType.hasMethodAnnotation(ExcludeFromCommonResponse.class)) {
			return false;
		}
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
		String exceptionMessage = GlobalExceptionHandler.getExceptionMessage();
		if (exceptionMessage != null && !exceptionMessage.equals("")) {
			GlobalExceptionHandler.clearExceptionMessage();
			return new CommonResponse(body, exceptionMessage);
		}

		String statusMessage = (String) request.getAttributes().get("statusMessage");

		if (statusMessage == null) {
			return new CommonResponse(body, "Request processed successfully");
		}

		return new CommonResponse(body, statusMessage);
	}
}