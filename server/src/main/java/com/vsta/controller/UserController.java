package com.vsta.controller;

import com.vsta.model.User;
import com.vsta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs using service methods for User.
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Allow User to register if data passes validity checks.
     * @param user User object of details input at registration.
     * @return  ResponseEntity with a status code and message
     *          indicating if user registration successful.
     */
    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * Request token for password reset then send email if valid User.
     * @param email Email specified by user.
     * @return  ResponseEntity with a status code and message
     *          indicating successful sending of email.
     */
    @PostMapping(value = "/resetPasswordRequest")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email) {
        return userService.resetPasswordRequest(email);
    }

}