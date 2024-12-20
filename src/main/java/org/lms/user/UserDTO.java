package org.lms.user;

import jakarta.validation.constraints.Email;

public class UserDTO {
    enum Role {
        ADMIN, STUDENT, INSTRUCTOR
    }
    private String firstName;
    private String lastName;
    private String password;
    @Email
    private String email;
    private Role role;

    public UserDTO(String firstName, String lastName, String password, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserModel [ firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", email=" + email + ", role=" + role + "]";
    }
}
