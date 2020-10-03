package com.portnet.controller.storage;

import com.portnet.entity.storage.User;
import com.portnet.service.storage.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;


    /**
     * Generic method to send email
     */

    @RequestMapping(value = "/sendEmail/{type}/name={name}&email={recipient}")
    public String sendEmail(@PathVariable String recipient, @PathVariable String name, @PathVariable String type) {
        return mailService.sendEmail(recipient, name, type);
    }

    /**
     * Specific methods for respective purposes
     * @param user user details will be passed in
     * @param attributes for redirection to auto email user
     * @return RedirectView to another link
     */

    @RequestMapping(value = "/resetUserPassword")
    public RedirectView resetUserPassword(@RequestBody User user, RedirectAttributes attributes) {
        attributes.addAttribute("type", "resetPassword");
        attributes.addAttribute("name", user.getName());
        attributes.addAttribute("recipient", user.getEmail());
        return new RedirectView("sendEmail/{type}/name={name}&email={recipient}");
    }

}
