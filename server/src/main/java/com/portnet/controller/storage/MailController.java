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
     */
    @RequestMapping(value = "/sendEmail")
    public ResponseEntity<String> sendEmail(@ModelAttribute("subject") String subject,
                                            @ModelAttribute("body") String body,
                                            @ModelAttribute("recipient") String recipient) {
        return mailService.sendEmail(subject, body, recipient);
    }

}
