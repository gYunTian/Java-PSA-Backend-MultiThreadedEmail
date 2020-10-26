package com.portnet.controller.storage;

import com.portnet.entity.storage.User;
import com.portnet.service.storage.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs using service methods for mail
 */

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * Generic method to send mail to user
     * @param user object (null if not found)
     * @return status message indicating that mail was successful
     */
    @RequestMapping(value = "/sendEmail")
    public ResponseEntity<String> sendEmail(@ModelAttribute("user") User user) {
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println("Request accepted");

        user.setToken();
        String body = "Hi " + user.getName() +",\n\n" +
                "We received a request to reset the password of your Portnet account.\n\n" +
                "You may use the following token to change your password:\n" +
                "" + user.getToken() + "\n\n" +
                "If you did not make such a request, kindly ignore this email.\n\n\n" +
                "Thank you!\n" +
                "G1T9";
        return mailService.sendEmail("Portnet Account Password Reset", body, user.getEmail());
    }

}
