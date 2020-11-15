package com.vsta.service;

import com.vsta.dto.UserNewPwDTO;
import com.vsta.model.User;
import com.vsta.utility.MailUtility;
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
public class NewPwService {

    @Autowired
    private UserService userService;

    @Autowired
    private MailUtility mailUtility;


    /**
     * Specific method to send mail to User
     * @param email User Email specified
     * @return  ResponseEntity with the given status code and message
     *          indicating successful sending of email
     */
    public ResponseEntity<String> resetPasswordRequest(String email) {
        User user = userService.getUserByEmail(email);  // if null, catch exception

        if (user == null) {
            return new ResponseEntity<>(
                    "Change Password unsuccessful - email not registered",
                    HttpStatus.BAD_REQUEST);
        }

        // if proceed, means user is not null so request accepted
        addToken(user); // generate password reset token for email body & save into database
        HashMap<String,String> emailContent = mailUtility.getEmailContent(user);
        return mailUtility.sendEmail(emailContent);
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
     * @param userNewPwDTO Token and new password input by User
     * @return  ResponseEntity with the given status code and message
     *          indicating if password reset successful
     *          (resetPassword output)
    */
    public ResponseEntity<String> resetPasswordController(UserNewPwDTO userNewPwDTO) {
//        User user = userService.getUserByEmail(userNewPwDTO.getEmail());

        String tokenGiven = userNewPwDTO.getIdentifier();
        User user = userService.getUserByToken(tokenGiven);

        if (user == null) {
            return new ResponseEntity<>(
                    "Change Password unsuccessful - wrong token",
                    HttpStatus.BAD_REQUEST);
        }

//        if (!user.getToken().equals(tokenGiven)) {
//            return new ResponseEntity<>(
//                    "Reset Password unsuccessful - wrong token",
//                    HttpStatus.BAD_REQUEST);
//        }

        return resetPassword(user, userNewPwDTO.getNewPassword());
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

        return ResponseEntity.ok("Password reset successful");
    }

//    /**
//     * Update password of password change requester
//     * @param userNewPwDTO Old and new password specified by user
//     * @return  ResponseEntity with the given status code and message
//     *          indicating if password change successful
//     */
//    public ResponseEntity<String> changePasswordController(UserNewPwDTO userNewPwDTO) {
//        User user = userService.getUserById(userNewPwDTO.getUserId());
//        String oldPasswordGiven = userNewPwDTO.getIdentifier();
//
//        if (!user.getPassword().equals(oldPasswordGiven)) {
//            return new ResponseEntity<>(
//                    "Change Password unsuccessful - wrong password",
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        user.setPassword(userNewPwDTO.getNewPassword());
//        userService.updateUser(user);
//        return ResponseEntity.ok("Password change successful");
//    }

}
