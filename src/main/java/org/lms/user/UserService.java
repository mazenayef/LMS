package org.lms.user;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO user) throws Exception{
        return userRepository.createUser(user);
    };

    public User updateUserById(Integer id, UserDTO user) throws Exception{
        return userRepository.updateUserById(id, user);
    };

    public void deleteUserById(Integer id){
        userRepository.deleteUserById(id);
    };

    public User findUserById(Integer id) throws Exception{
        return userRepository.findUserById(id);
    };

    public User findUserByEmail(String email) throws Exception{
        return userRepository.findUserByEmail(email);
    };

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String getEmailById(Integer id) throws Exception {
        return userRepository.getEmailById(id);
    }
}