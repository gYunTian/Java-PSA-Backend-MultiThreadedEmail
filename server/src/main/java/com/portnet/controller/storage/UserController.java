package com.portnet.controller.storage;

import com.portnet.entity.storage.User;
import com.portnet.service.storage.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
    public ResponseEntity<String> addUser(@RequestBody User user) {
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

        /**
         * sending email through url cuts the email for some reason, 
         * might be better to pass in as json (/loginUser)?
        */
    @GetMapping("/userByEmail/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

        /**
         * verify token @ password reset
         */
    @GetMapping("/userByToken/{token}")
    public User findUserByToken(@PathVariable String token) {
        return userService.getUserByToken(token);
    }

    /**
     * Update methods
     */

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody User user, String password) {
        return userService.changePassword(user, password);
    }

    /**
     * Request methods
     */

    @RequestMapping(value = "/changePasswordRequest")
    public RedirectView changePasswordRequest(@RequestParam String email, RedirectAttributes attrs) {
        return userService.changePasswordRequest(email, attrs);
    }

//    @RequestMapping(value = "/loginUser")
//    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String givenPassword) {
//        return userService.loginUser(email, givenPassword);
//    }
}