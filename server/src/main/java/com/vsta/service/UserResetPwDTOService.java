package com.vsta.service;

import com.vsta.dto.UserResetPwDTO;
import com.vsta.model.User;
import com.vsta.utility.ValidationUtil;
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
public class UserResetPwDTOService {

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

        // if empty fields, don't allow perform other checks
        ResponseEntity<String> emptyFieldsResponse = ValidationUtil.emptyFieldsResponse(userResetPwDTO.getAll(), errorMsgPrefix);
        if (emptyFieldsResponse != null) {
            return emptyFieldsResponse;
        }

        // no empty fields, proceed
        String emailGiven = userResetPwDTO.getEmail();
        User existingUser = userService.getUserByEmail(emailGiven);

        // if invalid, don't allow user to reset password
        ResponseEntity<String> invalidResponse = invalidResetPwResponse(existingUser, userResetPwDTO.getToken());
        if (invalidResponse != null) {
            return invalidResponse;
        }

        // since passed checks, request accepted
        String newPassword = userResetPwDTO.getPassword();
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
