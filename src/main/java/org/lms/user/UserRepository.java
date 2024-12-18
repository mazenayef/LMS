package org.lms.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import org.lms.user.User;

@Repository
public class UserRepository {

    User createUser (UserDTO user) throws Exception{
        User userCreated = new User();
        userCreated.setFirstName(user.getFirstName());
        userCreated.setLastName(user.getLastName());
        userCreated.setEmail(user.getEmail());
        userCreated.setPassword(user.getPassword());
        userCreated.setRole(user.getRole());
        userCreated.setId(UserDB.Users.size() + 1);
        if (UserDB.Users.stream().anyMatch(u -> u.getId().equals(userCreated.getId()))) {
            throw new Exception("User already exists");
        }
        else {
            // Check if the user have all data
            if (user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null || user.getEmail() == null || user.getRole() == null) {
                throw new Exception("User data is missing");
            }

            UserDB.Users.add(userCreated);
            return userCreated;
        }
    };

    User findUserById(Integer id) throws Exception{
        User userReturned = UserDB.Users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
        if (userReturned == null) {
            throw new Exception("User not found");
        }
        else {
            return userReturned;
        }
    };

    User findUserByEmail(String email) throws Exception{
        User userReturned = UserDB.Users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
        if (userReturned == null) {
            throw new Exception("User not found");
        }
        else {
            return userReturned;
        }
    };

    ArrayList<User> findAll() {
        return UserDB.Users;
    }

    User updateUserById (Integer id, UserDTO user) throws Exception {
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
            throw new Exception("User not found");
        }
        return userToUpdate;
    };

    void deleteUserById (Integer id){
        User userToDelete = UserDB.Users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        UserDB.Users.remove(userToDelete);
    };
}
