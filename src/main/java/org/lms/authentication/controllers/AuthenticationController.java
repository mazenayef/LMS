package org.lms.authentication.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lms.authentication.dtos.LoginDto;
import org.lms.authentication.dtos.TokenDto;
import org.lms.authentication.interceptors.CurrentUser;
import org.lms.authentication.interceptors.HasRole;
import org.lms.authentication.services.AuthenticationService;
import org.lms.user.User;
import org.lms.user.UserDTO;
import org.springframework.http.*;
import org.springframework.ui.Model;
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
	public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) throws Exception {
		TokenDto token = this.authenticationService.login(loginDto);
		ResponseCookie cookie = ResponseCookie.from("jwt", token.getToken())
				.httpOnly(true)
				.secure(true)
				.path("/")
				.sameSite("Strict")
				.build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(token);
	}

	@PostMapping("/register")
	public ResponseEntity<TokenDto> register(@RequestBody UserDTO userDto) throws Exception {
		TokenDto token = this.authenticationService.register(userDto);
		ResponseCookie cookie = ResponseCookie.from("jwt", token.getToken())
				.httpOnly(true)
				.secure(true)
				.path("/")
				.sameSite("Strict")
				.build();

		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(token);
	}

	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(@CurrentUser User currentUser, Model model) {
		return ResponseEntity.ok().body(currentUser);
	}
}
