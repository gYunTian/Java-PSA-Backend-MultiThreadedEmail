package com.vsta.utility;

import com.vsta.dto.UserDTO;
import com.vsta.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Mail Utility that send email to recipient
 */

@Service
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Generic method to send email to user.
     * @param emailContent object that contain subject, body and recipent for the email.
     * @return ResponseEntity with the given status code and message indicating that mail was successful.
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
     * Generic method to send mail to user for reset password/notification purposes.
     * @param user user object representing the requestor/subscriber of voyage.
     * @param subject the subject header for the email.
     * @param partBody the body of the email excluding greetings and sign off.
     * @return emailContent email content containing subject, body and recipient.
     */
    public HashMap<String,String> getEmailContent(UserDTO user, String subject, String partBody) {
        HashMap<String,String> emailContent = new HashMap<>();

        emailContent.put("recipient", user.getEmail());

        emailContent.put("subject", subject);

        String body = "Hi " + user.getName() + ",\n\n" + partBody +
                "Thank you!\n" +
                "G1T9";
        emailContent.put("body", body);

        return emailContent;
    }

}
