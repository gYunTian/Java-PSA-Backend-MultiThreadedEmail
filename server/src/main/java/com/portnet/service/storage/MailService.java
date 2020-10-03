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
    private JavaMailSender javaMailSender;

    public String sendEmail(String recipient, String name, String type) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(recipient);

        if (type.equals("resetPassword")) {
            msg.setSubject("xxxxxx is your Portnet account recovery code");
            msg.setText("Hi "+name+",\n" +
                    "We received a request to reset your Portnet password.\n" +
                    "Click here to change your password.\n" +
                    "Alternatively, you can enter the following password reset code:\n" +
                    ""
            );
        }

        javaMailSender.send(msg);
        return "Email sent successfully";
    }



}
