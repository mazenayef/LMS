package org.lms.authentication.interceptors;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lms.authentication.services.JwtService;
import org.lms.user.User;
import org.lms.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[0])
				.filter(cookie -> "jwt".equals(cookie.getName()))
				.findFirst();

		if (jwtCookie.isPresent()) {
			String token = jwtCookie.get().getValue();
			Claims claims = this.jwtService.extractAllClaims(token);
			try {
				User user = this.userService.findUserById(claims.get("id", Integer.class));
				request.setAttribute("userId", user.getId());
				request.setAttribute("userRole", user.getRole().toString());
				return true;
			} catch (Exception e) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType("application/json");
				response.getWriter().write("{\"error\": \"Unauthorized\"}");
				return false;
			}
		}

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		response.getWriter().write("{\"error\": \"Unauthorized\"}");
		return false;
	}
}
