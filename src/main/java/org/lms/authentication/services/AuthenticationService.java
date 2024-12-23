package org.lms.authentication.services;

import org.lms.authentication.dtos.LoginDto;
import org.lms.authentication.dtos.TokenDto;
import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.lms.user.User;
import org.lms.user.UserDTO;
import org.lms.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	private UserService userService;
	private JwtService jwtService;
	public AuthenticationService(UserService userService, JwtService jwtService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}

	public TokenDto login(LoginDto loginDto) throws Exception {
		User user = this.userService.findUserByEmail(loginDto.getEmail());

		if (user == null) {
			throw new HttpNotFoundException("User not found!");
		}

		if (!user.getPassword().equals(loginDto.getPassword())) {
			throw new HttpBadRequestException("Invalid Password");
		}

		String token = this.jwtService.generateToken(user.getId(), user.getRole());
		return new TokenDto(token);
	}

	public TokenDto register(UserDTO userDto) throws Exception {
		User user = this.userService.createUser(userDto);

		String token = this.jwtService.generateToken(user.getId(), user.getRole());
		return new TokenDto(token);
	}

	public User getUser(Integer id) throws Exception {
		return this.userService.findUserById(id);
	}
}
