package com.vsta.service;

import com.vsta.dto.UserLoginDTO;
import com.vsta.model.User;
import com.vsta.utility.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User Login Service tasks used for REST APIs to validate Login requests.
 */

@Service
public class UserLoginDTOService {

    @Autowired
    UserService userService;

    // Response messages
    final String errorMsgPrefix = "Login unsuccessful - ";

    final String nonExistentEmailMsg = errorMsgPrefix + "email not registered";
    final String wrongPasswordMsg = errorMsgPrefix + "wrong password";

    final String successMsg = "Login successful";

    /**
     * Check if registered email passes validity checks.
     * @param existingUser User object specified to perform validation.
     * @param givenPassword Password specified by User.
     * @return  ResponseEntity with an error message and Bad Request status code if invalid,
     *          else return null.
     */
    private ResponseEntity<String> invalidLoginResponse(User existingUser, String givenPassword) {

        if (existingUser == null) {
            return new ResponseEntity<>(nonExistentEmailMsg, HttpStatus.BAD_REQUEST);
        }
        if (!new BCryptPasswordEncoder().matches(givenPassword, existingUser.getPassword())) {
            return new ResponseEntity<>(wrongPasswordMsg, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    /**
     * Allow User to login if data passes validity checks.
     * @param userLoginDTO Email and password specified by user.
     * @return  ResponseEntity with a status code and message
     *          indicating if user registration successful.
     */
    public ResponseEntity<String> verifyUser(UserLoginDTO userLoginDTO) {

        // if empty fields, don't allow perform other checks
        ResponseEntity<String> emptyFieldsResponse = ValidationUtil.emptyFieldsResponse(userLoginDTO.getAll(), errorMsgPrefix);
        if (emptyFieldsResponse != null) {
            return emptyFieldsResponse;
        }

        // no empty fields, proceed
        String email = userLoginDTO.getEmail();
        User existingUser = userService.getUserByEmail(email);

        // if invalid, don't allow user to login
        ResponseEntity<String> invalidResponse = invalidLoginResponse(existingUser, userLoginDTO.getPassword());
        if (invalidResponse != null) {
            return invalidResponse;
        }

        // since passed checks, login details are valid, allow user to login
        String responseMessage =
                "User " + existingUser.getId() + ", " +
                existingUser.getName() + "'s " +
                successMsg;

        return ResponseEntity.ok(responseMessage);
    }

}
