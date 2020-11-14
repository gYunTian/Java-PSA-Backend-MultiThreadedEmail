package com.vsta.controller;

import com.vsta.dto.LoginDTO;
import com.vsta.dto.NewPasswordDTO;
import com.vsta.entity.User;
import com.vsta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
     * Add method - Add user into database
     * @param user user object to be added into database
     * @return ResponseEntity with the given status code and message indicating if user is added successfully
     */

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * Get method - Get all users stored in database
     * @return a list of user objects
     */

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.getUsers();
    }

    /**
     * Get method - Get user with specified id in database
     * @param id the auto-generated ID of the user
     * @return a list of user objects
     */

    @GetMapping("/userById/{id}")
    public User findUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * Update method - Reset user's password
     * @param newPasswordDTO containing the token and new password input by user
     * @return ResponseEntity with the given status code and message indicating if change password successful
     */

//    @PutMapping("/changePassword")
//    public ResponseEntity<String> changePassword(@RequestBody NewPasswordDTO newPasswordDTO) {
//        return userService.changePasswordController(newPasswordDTO);
//    }

    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody NewPasswordDTO newPasswordDTO) {
        return userService.resetPasswordController(newPasswordDTO);
    }

    /**
     * Request method - Receive reset password request and send email
     * @param email the email registered by the User
     * @return ResponseEntity with the given status code and message indicating successful sending of email
     */

    @RequestMapping(value = "/resetPasswordRequest")
    public ResponseEntity<String> resetPasswordRequest(
            @RequestHeader(
                name = "authorization",
                defaultValue = "Basic ZzF0OTo5OTkwMDA=")
            @RequestParam String email) {
        return userService.resetPasswordRequest(email);
    }

    /**
     * Request method - Allow User to login if data passes validity checks
     * @param loginDTO containing the email and password of user
     * @return ResponseEntity with the given status code and message indicating if user registration successful
     */

    @RequestMapping(value = "/loginUser")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        return userService.loginUser(loginDTO);
    }

    /**
     * Delete method - Remove specified User from database
     * @param user object that requested deactivation of account
     * @return ResponseEntity with the given status code and message indicating if user is deleted successfully
     */

    @DeleteMapping("/deactivateUser")
    public ResponseEntity<String> deactivateUser(@RequestBody User user) {
        return userService.deleteUser(user);
    }

}