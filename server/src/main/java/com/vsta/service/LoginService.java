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
public class LoginService {

    @Autowired
    UserService userService;

    /**
     * Allow User to login if data passes validity checks
     * @param userLoginDTO Email and password specified by user
     * @return  ResponseEntity with the given status code and message
     *          indicating if user registration successful
     */
    public ResponseEntity<String> verifyUser(UserLoginDTO userLoginDTO) {
        String email = userLoginDTO.getEmail();
        String givenPassword = userLoginDTO.getPassword();

        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(
                    "Login unsuccessful - wrong email",
                    HttpStatus.BAD_REQUEST);
        }
        if (!new BCryptPasswordEncoder().matches(givenPassword, user.getPassword())) {
            return new ResponseEntity<>(
                    "Login unsuccessful - wrong password",
                    HttpStatus.BAD_REQUEST);
        }

        String responseMessage = String.format(
                "User %d, %s's Login successful - details valid",
                user.getId(), user.getName());

        return ResponseEntity.ok(responseMessage);
    }
}
