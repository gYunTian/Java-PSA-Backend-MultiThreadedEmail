package com.vsta.service;

import com.vsta.dto.UserLoginDTO;
import com.vsta.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User Login Service tasks
 * used for REST APIs to
 * validate Login requests.
 */

@Service
public class UserLoginDTOService {

    @Autowired
    UserService userService;


    final String errorMsgPrefix = "Login unsuccessful - ";
    final String nonExistentEmailMsg = errorMsgPrefix + "email not registered";
    final String wrongPasswordMsg = errorMsgPrefix + "wrong password";

    final String successMsg = "Login successful";

    /**
     * Check if registered email passes validity checks
     * @return  ResponseEntity with an error message and Bad Request status code if invalid,
     *          else return null
     */
    public ResponseEntity<String> invalidLoginResponse(User existingUser, String givenPassword) {

        if (existingUser == null) {
            return new ResponseEntity<>(nonExistentEmailMsg, HttpStatus.BAD_REQUEST);
        }
        if (!new BCryptPasswordEncoder().matches(givenPassword, existingUser.getPassword())) {
            return new ResponseEntity<>(wrongPasswordMsg, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    /**
     * Allow User to login if data passes validity checks
     * @param userLoginDTO Email and password specified by user
     * @return  ResponseEntity with a status code and message
     *          indicating if user registration successful
     */
    public ResponseEntity<String> verifyUser(UserLoginDTO userLoginDTO) {
        String email = userLoginDTO.getEmail();
        String givenPassword = userLoginDTO.getPassword();

        User existingUser = userService.getUserByEmail(email);

        // if invalid, don't allow user to login
        ResponseEntity<String> invalidResponse = invalidLoginResponse(existingUser, givenPassword);
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
