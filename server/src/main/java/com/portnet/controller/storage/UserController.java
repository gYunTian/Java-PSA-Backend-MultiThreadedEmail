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
    private UserService userService;


    /**
     * Add methods
     */

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * Get methods
     */

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/userById/{id}")
    public User findUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/userByEmail/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    /**
     * Update methods
     */

    @PutMapping("/updateUser")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

}