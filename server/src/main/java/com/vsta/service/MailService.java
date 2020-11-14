package com.vsta.service;

import com.vsta.entity.User;
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
     * @param emailContent object that contain subject, body and recipent for the email
     * @return ResponseEntity with the given status code and message indicating that mail was successful
     */
    public ResponseEntity<String> sendEmail(HashMap<String,String> emailContent) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setSubject(emailContent.get("subject"));
        email.setText(emailContent.get("body"));
        email.setTo(emailContent.get("recipient"));
        
        mailSender.send(email);
        return ResponseEntity.ok("Email successfully sent");
    }

    /**
     * Generic method to send mail to user for reset password purpose
     * @param user user object representing the requestor
     * @return emailContent email content containing subject, body and recipient
     */
    public HashMap<String,String> getEmailContent(User user) {
        HashMap<String,String> emailContent = new HashMap<>();

        emailContent.put("recipient", user.getEmail());

        // customise subject & body
        emailContent.put("subject", "Vsta Account Password Reset");

        String body = "Hi " + user.getName() + ",\n\n" +
                "We received a request to reset the password of your Vsta account.\n\n" +
                "You may use the following token to change your password:\n" +
                "" + user.getToken() + "\n\n" +
                "If you did not make such a request, kindly ignore this email.\n\n\n" +
                "Thank you!\n" +
                "G1T9";
        emailContent.put("body", body);

        return emailContent;
    }

    /**
     * Overloaded getEmailContent method
     * with different implementation as well
     * @param user user object representing the requestor
     * @param changes message on the changes for vessel
     * @param id auto-generated ID of the vessel
     * @return emailContent email content containing subject, body and recipient
     */
    public HashMap<String,String> getEmailContent(User user, String changes, String id) {
        HashMap<String,String> emailContent = new HashMap<>();

        emailContent.put("recipient", user.getEmail());

        emailContent.put("subject", "Changes to vessel detail: "+id);

        String content = "Hi " + user.getName() + ",\n\n" +
                "Details of the vessel: "+id+" has changed.\n\n" +
                changes + "\n" +
                "Thank you!\n" +
                "G1T9";
        emailContent.put("body", content);

        return emailContent;
    }
}
