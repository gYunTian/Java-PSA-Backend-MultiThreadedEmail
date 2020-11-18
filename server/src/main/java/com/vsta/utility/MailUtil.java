package com.vsta.utility;

import com.vsta.dto.UserDTO;
import com.vsta.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Mail Utility that send email to recipient
 */

@Service
public class MailUtil {

        @Autowired
        private JavaMailSender mailSender;

        /**
         * Generic method to send email to user.
         * 
         * @param user     User object representing the recipient.
         * @param subject  Subject header for the email.
         * @param partBody Body of the email excluding greetings and sign off.
         * @return ResponseEntity with a status code and message indicating that mail
         *         was successful.
         */
        public ResponseEntity<String> sendEmail(User user, String subject, String partBody) {
                SimpleMailMessage email = new SimpleMailMessage();

                email.setTo(user.getEmail());
                email.setSubject(subject);

                String body = "Hi " + user.getName() + ",\n\n" + partBody + "\n\n\n" + "Thank you!\n" + "G1T9";
                email.setText(body);

                mailSender.send(email);
                return ResponseEntity.ok("Email successfully sent to " + user.getEmail());
        }

        /**
         * An overloaded method to send email to user.
         * 
         * @param user  UserDTO object, including name and email of the recipient, .
         * @param subject  Subject header for the email.
         * @param partBody Body of the email excluding greetings and sign off.
         * @return ResponseEntity with a status code and message indicating that mail
         *         was successful.
         */
        public ResponseEntity<String> sendEmail(UserDTO user, String subject, String partBody) {
                SimpleMailMessage email = new SimpleMailMessage();

                email.setTo(user.getEmail());
                email.setSubject(subject);

                String body = "Hi " + user.getName() + ",\n\n" + partBody + "\n\n\n" + "Thank you!\n" + "G1T9";
                email.setText(body);

                mailSender.send(email);
                return ResponseEntity.ok("Email successfully sent to " + user.getEmail());
        }

}
