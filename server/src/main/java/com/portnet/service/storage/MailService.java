package com.portnet.service.storage;

import com.portnet.entity.storage.User;
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
     * Generic method to send mail to user (for controller)
     * @param user object representing the requester
     * @param type email purpose
     * @return status message indicating that mail was successful
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
     * Get content of reset password email (helper method)
     * @param user object representing the requester
     * @param type email purpose
     * @return email content containing subject, body and recipient
     */
    public HashMap<String,String> getEmailContent(User user, String type) {
        HashMap<String,String> emailContent = new HashMap<>();

        emailContent.put("recipient", user.getEmail());

        // customise subject & body
        if (type.equals("resetPasswordRequest")) {
            emailContent.put("subject", "Portnet Account Password Reset");

            String body = "Hi " + user.getName() + ",\n\n" +
                    "We received a request to reset the password of your Portnet account.\n\n" +
                    "You may use the following token to change your password:\n" +
                    "" + user.getToken() + "\n\n" +
                    "If you did not make such a request, kindly ignore this email.\n\n\n" +
                    "Thank you!\n" +
                    "G1T9";
            emailContent.put("body", body);
        }

        return emailContent;
    }

    /**
     * Overloaded getEmailContent method
     * with different implementation as well
     * @param email
     * @param body
     * @return
     */
    public HashMap<String,String> getEmailContent(String email, String body, String id) {
        HashMap<String,String> emailContent = new HashMap<>();

        emailContent.put("recipient", email);

        emailContent.put("subject", "Changes to vessel detail: "+id);

        String content = "Hi " + email + ",\n\n" +
                "Details of the vessel: "+id+" has changed.\n\n" +
                body + "\n" +
                "Thank you!\n" +
                "G1T9";
        emailContent.put("body", content);

        return emailContent;
    }
}
