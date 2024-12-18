package org.lms.user;


import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@EntityScan
public class User {
    enum Role {
        ADMIN, STUDENT, INSTRUCTOR
    }

    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Enum role;

    public User() {
    }

    public User(Integer id, String firstName, String lastName, String password, String email, Enum role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Enum getRole() {
        return role;
    }

    public void setRole(Enum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", email=" + email + ", role=" + role + "]";
    }
}

