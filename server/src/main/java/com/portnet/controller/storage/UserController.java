package com.portnet.controller.storage;

import com.portnet.entity.storage.User;
import com.portnet.service.storage.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
     * @param email the email registered by the User
     * @param attrs to store & bring email content to the next view
     * @return RedirectView object to the mail view
     */
    @RequestMapping(value = "/changePasswordRequest")
    public RedirectView changePasswordRequest(@RequestParam String email, RedirectAttributes attrs) {
        HashMap<String,String> emailContent = userService.changeUserPasswordRequest(email);

        Set<String> keys = emailContent.keySet();
        for (String key : keys) {
            attrs.addFlashAttribute(key, emailContent.get(key));
        }
        return new RedirectView("sendEmail");
    }

}