package org.lms.user;

import org.lms.authentication.annotations.CurrentUser;
import org.lms.authentication.annotations.HasRole;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/")
    @HasRole({"ADMIN"})
    public ResponseEntity<User> createUser(@RequestBody UserDTO user, @CurrentUser User currentUser) throws Exception{
        return ResponseEntity.ok().body(userService.createUser(user));
    };

    @PatchMapping("/{id}")
    @HasRole({"ADMIN"})
    public ResponseEntity<User> updateUserById(@PathVariable("id") Integer id, @RequestBody UserDTO user, @CurrentUser User currentUser) throws Exception{
        return ResponseEntity.ok().body(userService.updateUserById(id, user));
    };

    @DeleteMapping("/{id}")
    @HasRole({"ADMIN"})
    public ResponseEntity deleteUserById(@PathVariable("id") Integer id, @CurrentUser User currentUser) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    };

    @GetMapping("/{id}")
    @HasRole({"ADMIN", "INSTRUCTOR"})
    public ResponseEntity<User> findUserById(@PathVariable("id") String id, @CurrentUser User currentUser) throws Exception{
        Integer idUsed = Integer.parseInt(id);
        return ResponseEntity.ok().body(userService.findUserById(idUsed));
    };

    @GetMapping("/")
    @HasRole({"ADMIN"})
    public ResponseEntity<List<User>> findAllUsers(@CurrentUser User currentUser) {
        return ResponseEntity.ok().body(userService.findAll());
    }
}