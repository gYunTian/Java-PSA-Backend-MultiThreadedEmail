package com.vsta.controller;

import com.vsta.dto.LoginDTO;
import com.vsta.dto.NewPasswordDTO;
import com.vsta.model.User;
import com.vsta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs using service methods for User
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Allow User to register if data passes validity checks
     * @param user User object of details input at registration
     * @return  ResponseEntity with the given status code and message
     *          indicating if user registration successful
     */
    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }


    /**
     * Check if User login details are valid
     * @param loginDTO Email and password specified by user
     * @return  ResponseEntity with the given status code and message
     *          indicating if user login successful
     */
    @RequestMapping(value = "/loginUser")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        return userService.loginUser(loginDTO);
    }

//    /**
//     * Get user with specified id in database
//     * @param id Auto-generated ID of the user
//     * @return List of user objects
//     */
//    @GetMapping("/userById/{id}")
//    public User findUserById(@PathVariable int id) {
//        return userService.getUserById(id);
//    }

//    /**
//     * Update password of password reset requester
//     * @param newPasswordDTO Old and new password specified by user
//     * @return  ResponseEntity with the given status code and message
//     *          indicating if password reset successful
//     */
//    @PutMapping("/changePassword")
//    public ResponseEntity<String> changePassword(@RequestBody NewPasswordDTO newPasswordDTO) {
//        return userService.changePasswordController(newPasswordDTO);
//    }

    /**
     * Update password of password change requester
     * @param newPasswordDTO Token and new password specified by user
     * @return  ResponseEntity with the given status code and message
     *          indicating if password change successful
     */
    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody NewPasswordDTO newPasswordDTO) {
        return userService.resetPasswordController(newPasswordDTO);
    }

    /**
     * Request token for reset password then send email if valid User
     * @param email Email specified by user
     * @return  ResponseEntity with the given status code and message
     *          indicating successful sending of email
     */
    @RequestMapping(value = "/resetPasswordRequest")
    public ResponseEntity<String> resetPasswordRequest(
            @RequestHeader(
                name = "authorization",
                defaultValue = "Basic ZzF0OTo5OTkwMDA=")
            @RequestParam String email) {
        return userService.resetPasswordRequest(email);
    }

}