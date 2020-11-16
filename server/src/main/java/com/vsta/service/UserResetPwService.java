package com.vsta.service;

import com.vsta.dto.UserResetPwDTO;
import com.vsta.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * User New Password Service tasks
 * used for REST APIs to validate
 * Reset/Change Password requests.
 */

@Service
public class UserResetPwService {

    @Autowired
    private UserService userService;


    final String errorMsgPrefix = "Reset Password unsuccessful - ";

    final String nonExistentEmailMsg = errorMsgPrefix + "email not registered";
    final String wrongTokenMsg = errorMsgPrefix + "wrong token";

    final String successMsg = "Password reset successful";

    /**
     * If data passes validity checks, update password and remove reset token of User
     * @param userResetPwDTO Token and new password input by User
     * @return  ResponseEntity with a status code and message
     *          indicating if password reset successful
     */
    public ResponseEntity<String> resetPassword(UserResetPwDTO userResetPwDTO) {
        User existingUser = userService.getUserByEmail(userResetPwDTO.getEmail());
        String tokenGiven = userResetPwDTO.getToken();

        ResponseEntity<String> invalidResponse = invalidResetPwResponse(existingUser, tokenGiven);
        if (invalidResponse != null) {
            return invalidResponse;
        }

        // since passed checks, request accepted
        String newPassword = userResetPwDTO.getNewPassword();
        existingUser.setPassword(newPassword);

        // remove token
        existingUser.setToken(null);
        userService.updateUser(existingUser);

        return ResponseEntity.ok(successMsg);
    }

    /**
     * Check if specified details passes validity checks
     * @param existingUser User object if email exists in database, else null
     * @param tokenGiven token specified by User
     * @return  ResponseEntity with an error message and 400 status code if invalid,
     *          else null
     */
    public ResponseEntity<String> invalidResetPwResponse(User existingUser, String tokenGiven) {

        if (existingUser == null) {
            return new ResponseEntity<>(nonExistentEmailMsg, HttpStatus.BAD_REQUEST);
        }
        if (!existingUser.getToken().equals(tokenGiven)) {
            return new ResponseEntity<>(wrongTokenMsg, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

}
