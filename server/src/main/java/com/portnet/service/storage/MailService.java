package com.portnet.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service that send email to recipient
 */

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendEmail(String subject, String body, String recipient) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setSubject(subject);
        email.setText(body);
        email.setTo(recipient);

        mailSender.send(email);
        return "Email successfully sent";
    }

}
