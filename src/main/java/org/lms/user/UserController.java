package org.lms.user;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.lms.user.UserDB;

// Solved the issue by adding exclude = {DataSourceAutoConfiguration.class } to the @SpringBootApplication annotation
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println(UserDB.Users);
        return "Test";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable("id") Integer id, @RequestBody UserDTO user){
        try {
            return ResponseEntity.ok().body(userService.updateUserById(id, user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    };

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    };

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") String id){
        Integer idUsed = Integer.parseInt(id);
        try {
            return ResponseEntity.ok().body(userService.findUserById(idUsed));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    };

    @GetMapping("/")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }
}