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

	public GetUserDto() {}


	static public GetUserDto fromUser(User user) {
		return new GetUserDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
	}

	static public List<GetUserDto> fromUsers(List<User> users) {
		return users.stream().map(GetUserDto::fromUser).collect(Collectors.toList());
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User.Role getRole() {
		return role;
	}

	public void setRole(User.Role role) {
		this.role = role;
	}
}
