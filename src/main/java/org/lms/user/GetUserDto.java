package org.lms.user;

import jakarta.validation.constraints.Email;

import java.util.List;
import java.util.stream.Collectors;

public class GetUserDto {
	private String firstName;
	private String lastName;
	private String email;
	private User.Role role;

	public GetUserDto(String firstName, String lastName, String email, User.Role role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}

	static public GetUserDto fromUser(User user) {
		return new GetUserDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
	}

	static public List<GetUserDto> fromUsers(List<User> users) {
		return users.stream().map(GetUserDto::fromUser).collect(Collectors.toList());
	}
}
