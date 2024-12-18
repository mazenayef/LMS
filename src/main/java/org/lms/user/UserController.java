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
@RequestMapping("/users")
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

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        try {
            return ResponseEntity.ok().body(userService.createUser(user));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    };

    @PutMapping("/update/{id}")
    public ResponseEntity<User>  updateUserById(@PathVariable Integer id,@RequestBody UserDTO user){
        try {
            return ResponseEntity.ok().body(userService.updateUserById(id, user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    };

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
    };

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Integer id){
        try {
            return ResponseEntity.ok().body(userService.findUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    };

    @GetMapping("/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email){
        try {
            return ResponseEntity.ok().body(userService.findUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    };

    @GetMapping("/")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }
}