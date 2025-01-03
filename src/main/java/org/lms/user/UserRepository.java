package org.lms.user;

import org.lms.shared.exceptions.HttpBadRequestException;
import org.lms.shared.exceptions.HttpNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import org.lms.user.User;

@Repository
public class UserRepository {

    public User createUser (UserDTO user) throws Exception{
        User userCreated = new User();
        userCreated.setFirstName(user.getFirstName());
        userCreated.setLastName(user.getLastName());
        userCreated.setEmail(user.getEmail());
        userCreated.setPassword(user.getPassword());
        userCreated.setRole(user.getRole());
        userCreated.setId(UserDB.Users.size() + 1);
        if (UserDB.Users.stream().anyMatch(u -> u.getEmail().equals(userCreated.getEmail()))) {
            throw new HttpBadRequestException("Email already exists");
        }
        else {
            // Check if the user have all data
            if (user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null || user.getEmail() == null || user.getRole() == null) {
                throw new HttpBadRequestException("User data is missing");
            }
            if (user.getRole() != User.Role.ADMIN && user.getRole() != User.Role.INSTRUCTOR && user.getRole() != User.Role.STUDENT) {
                throw new HttpBadRequestException("Invalid role");
            }

            UserDB.Users.add(userCreated);
            return userCreated;
        }
    };

    public User findUserById(Integer id) throws Exception{
        User userReturned = UserDB.Users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
        if (userReturned == null) {
            throw new HttpNotFoundException("User not found");
        }
        else {
            return userReturned;
        }
    };

    public User findUserByEmail(String email) throws Exception{
        User userReturned = UserDB.Users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
        if (userReturned == null) {
            throw new HttpNotFoundException("User not found");
        }
        else {
            return userReturned;
        }
    };

    public ArrayList<User> findAll() {
        return UserDB.Users;
    }

    public User updateUserById (Integer id, UserDTO user) throws Exception {
        User userToUpdate = UserDB.Users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        if (userToUpdate != null) {
            if (user.getFirstName() != null) {
                userToUpdate.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                userToUpdate.setLastName(user.getLastName());
            }
            if (user.getEmail() != null) {
                userToUpdate.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                userToUpdate.setPassword(user.getPassword());
            }
            if (user.getRole() != null) {
                userToUpdate.setRole(user.getRole());
            }
        }
        else {
            throw new HttpNotFoundException("User not found");
        }
        return userToUpdate;
    };

    public void deleteUserById(Integer id){
        User userToDelete = UserDB.Users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        UserDB.Users.remove(userToDelete);
    };

    public String getEmailById (Integer id){
        User user = UserDB.Users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        if (user == null) {
            return null;
        }
        return user.getEmail();
    }
}
