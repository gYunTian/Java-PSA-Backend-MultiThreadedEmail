package com.portnet.controller.storage;

import com.portnet.entity.storage.User;
import com.portnet.service.storage.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * REST APIs using service methods for mail
 */

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * Generic method that handles the sending of email to requesters
     * - if email exists: sends email to user & return ok response
     * - if email DNE: return bad request response 
     */
    @RequestMapping(value = "/sendEmail")
    public ResponseEntity<String> sendEmail(@ModelAttribute("user") User user,
                                            @ModelAttribute("type") String type) {
        
                                                
        if (type.equals("rejectRequest")) {
            return new ResponseEntity<>(
                    "Change Password unsuccessful - email not registered",
                    HttpStatus.BAD_REQUEST);
        }
        
        HashMap<String,String> emailContent = mailService.getEmailContent(user, type);
        return mailService.sendEmail(emailContent);
    }

}
