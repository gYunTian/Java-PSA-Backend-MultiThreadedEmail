package com.portnet.controller.storage;

import com.portnet.service.storage.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class MailController {

    @Autowired
    private MailService service;

    /**
     * Request methods
     */

    @RequestMapping(value = "/sendemail/{recipent}")
    public String sendEmail(@PathVariable String recipent) throws IOException, MessagingException {
        service.sendEmail(recipent);
        return "Email sent successfully";
    }
}
