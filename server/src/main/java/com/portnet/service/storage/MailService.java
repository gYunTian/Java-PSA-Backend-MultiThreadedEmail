package com.portnet.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Service that send email to recipient
 */

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Generic method to send mail to user
     * @param subject email header
     * @param body email content
     * @param recipient registered email of the requester
     * @return status message indicating that mail was successful
     */
    public ResponseEntity<String> sendEmail(String subject, String body, String recipient) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setSubject(subject);
        email.setText(body);
        email.setTo(recipient);

        mailSender.send(email);
        return ResponseEntity.ok("Email successfully sent");
    }

    /**
     * Get email content for UserService
     * @param subject email header
     * @param body email content
     * @param recipient registered email of the requester
     * @return status message indicating that mail was successful
     */
    public HashMap<String,String> getEmailContent(String subject, String body, String recipient) {
        HashMap<String,String> emailContent = new HashMap<>();

        emailContent.put("subject", subject);
        emailContent.put("body", body);
        emailContent.put("recipient", recipient);

        return emailContent;
    }

}
