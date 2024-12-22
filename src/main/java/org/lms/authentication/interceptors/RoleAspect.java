package org.lms.authentication.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleAspect {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Before("@annotation(hasRole)")
	public void checkRole(HasRole hasRole) throws Exception {
		boolean hasRequiredRole = false;
		String role = (String) request.getAttribute("userRole");

		for (String requiredRole : hasRole.value()) {
			if (requiredRole.equals(role)) {
				hasRequiredRole = true;
				break;
			}
		}

		if (!hasRequiredRole) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType("application/json");
			response.getWriter().write("{\"error\": \"Forbidden\"}");
			response.getWriter().flush();
			return;
		}
	}
}
