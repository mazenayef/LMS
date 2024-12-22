package org.lms.authentication.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.lms.authentication.dtos.LoginDto;
import org.lms.authentication.dtos.TokenDto;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.authentication.services.AuthenticationService;
import org.lms.user.User;
import org.lms.user.UserDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	private AuthenticationService authenticationService;
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
		try {
			TokenDto token = this.authenticationService.login(loginDto);
			ResponseCookie cookie = ResponseCookie.from("jwt", token.getToken())
					.httpOnly(true)
					.secure(true)
					.path("/")
					.sameSite("Strict")
					.build();

			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/register")
	public ResponseEntity<TokenDto> register(@RequestBody UserDTO userDto) {
		try {
			TokenDto token = this.authenticationService.register(userDto);
			ResponseCookie cookie = ResponseCookie.from("jwt", token.getToken())
					.httpOnly(true)
					.secure(true)
					.path("/")
					.sameSite("Strict")
					.build();

			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(@CurrentUser User currentUser) {
		try {
			return ResponseEntity.ok().body(currentUser);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}
