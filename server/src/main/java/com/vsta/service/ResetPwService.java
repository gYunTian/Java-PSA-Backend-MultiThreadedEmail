package com.vsta.service;

import com.vsta.dto.UserResetPwDTO;
import com.vsta.model.User;
import com.vsta.utility.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * User New Password Service tasks
 * used for REST APIs to validate
 * Reset/Change Password requests.
 */

@Service
public class ResetPwService {

    @Autowired
    private UserService userService;

    @Autowired
    private MailUtil mailUtil;


    final String errorMsgPrefix = "Reset Password unsuccessful - ";
    final String nonExistentEmailMsg = errorMsgPrefix + "email not registered";
    final String wrongTokenMsg = errorMsgPrefix + "wrong token";

    final String successMsg = "Password reset successful";

    /**
     * Specific method to send mail to User
     * @param email User Email specified
     * @return  ResponseEntity with the given status code and message
     *          indicating successful sending of email
     */
    public ResponseEntity<String> resetPasswordRequest(String email) {
        User user = userService.getUserByEmail(email);

        ResponseEntity<String> emailValidationResult = userService.nullUserResult(user, nonExistentEmailMsg);
        if (emailValidationResult != null) return emailValidationResult;

        // if proceed, means user is not null so request accepted
        addToken(user); // generate password reset token for email body & save into database

        String subject = "Vsta Account Password Reset";

        String body = "We received a request to reset the password of your Vsta account.\n\n" +
                "You may use the following token to change your password:\n" +
                "" + user.getToken() + "\n\n" +
                "If you did not make such a request, kindly ignore this email.\n\n\n";

        HashMap<String,String> emailContent = mailUtil.getEmailContent(user,subject,body);

        return mailUtil.sendEmail(emailContent);
    }
    /**
     * Generate and save token of User who successfully requested password reset
     * @param user User object representing the requester
     */
    public void addToken(User user) {
        user.setToken();
        userService.updateUser(user);
    }

    /**
     * Allow User to reset password if data passes validity checks
     * @param userResetPwDTO Token and new password input by User
     * @return  ResponseEntity with the given status code and message
     *          indicating if password reset successful
     *          (resetPassword output)
    */
    public ResponseEntity<String> resetPasswordController(UserResetPwDTO userResetPwDTO) {
        User user = userService.getUserByEmail(userResetPwDTO.getEmail());

        ResponseEntity<String> emailValidationResult = userService.nullUserResult(user, nonExistentEmailMsg);
        if (emailValidationResult != null) return emailValidationResult;

        String tokenGiven = userResetPwDTO.getToken();
        if (!user.getToken().equals(tokenGiven)) {
            return new ResponseEntity<>(wrongTokenMsg, HttpStatus.BAD_REQUEST);
        }

        return resetPassword(user, userResetPwDTO.getNewPassword());
    }
    /**
     * Update password and remove reset token of specified User
     * @param user User object that has been verified to be correct
     * @param password New password chosen by User
     * @return  ResponseEntity with the given status code and message
     *          indicating if password reset successful
     */
    public ResponseEntity<String> resetPassword(User user, String password) {
        user.setPassword(password);

        // remove token
        user.setToken(null);
        userService.updateUser(user);

        return ResponseEntity.ok(successMsg);
    }

}
