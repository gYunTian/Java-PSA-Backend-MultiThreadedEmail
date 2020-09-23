package com.portnet.controller.storage;

import com.portnet.entity.storage.User;
import com.portnet.service.storage.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for User
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Add methods
     */

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
        service.saveUser(user);
    }

    @PostMapping("/addUsers")
    public void addUser(@RequestBody List<User> users) {
        service.saveUsers(users);
    }

    /**
     * Get methods
     */

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return service.getUsers();
    }

    @GetMapping("/userById/{id}")
    public User findUserById(@PathVariable int id) {
        return service.getUser(id);
    }

    @GetMapping("/user/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return service.getUser(email);
    }

    /**
     * Update methods
     */

    @PutMapping("/updateUser")
    public void updateUser(@RequestBody User user) {
        service.updateUser(user);
    }
}