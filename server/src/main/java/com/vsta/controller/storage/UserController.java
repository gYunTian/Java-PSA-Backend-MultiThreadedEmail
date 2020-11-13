package com.vsta.controller.storage;

import com.vsta.entity.dto.LoginDTO;
import com.vsta.entity.dto.NewPasswordDTO;
import com.vsta.entity.storage.User;
import com.vsta.service.storage.UserService;
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
     * Update methods
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
     * Request methods
     */

    @RequestMapping(value = "/resetPasswordRequest")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email) {
        return userService.resetPasswordRequest(email);
    }

    @RequestMapping(value = "/loginUser")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        return userService.loginUser(loginDTO);
    }

    /**
     * Delete methods
     */

    @DeleteMapping("/deactivateUser")
    public ResponseEntity<String> deactivateUser(@RequestBody User user) {
        return userService.deleteUser(user);
    }

}