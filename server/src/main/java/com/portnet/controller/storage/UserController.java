package com.portnet.controller.storage;

import com.portnet.entity.storage.User;
import com.portnet.service.storage.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Update methods
     */

    @PutMapping("/changePassword")
    public void changePassword(@RequestBody User user, String password) {
        userService.changeUserPassword(user, password);
    }

    /**
     * Specific method to send mail to user for respective purposes
     * @param user object
     * @param attributes to store & bring user object to next view
     * @return RedirectView to another link
     */

    @RequestMapping(value = "/changePasswordRequest")
    public RedirectView changePasswordReq(@RequestBody User user, RedirectAttributes attributes) {
        user.setToken();
        attributes.addFlashAttribute("user", user);
        return new RedirectView("sendEmail");
    }
}