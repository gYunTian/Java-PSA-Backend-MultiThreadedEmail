package com.vsta.controller;

import com.vsta.dto.UserResetPwDTO;
import com.vsta.service.UserResetPwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs using service methods for User Reset/Change Password
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserResetPwDTOController {

    @Autowired
    private UserResetPwService userResetPwService;


    /**
     * Update password of password reset requester.
     * @param userResetPwDTO Token and reset password specified by user.
     * @return  ResponseEntity with a status code and message
     *          indicating if password change successful.
     */
    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody UserResetPwDTO userResetPwDTO) {
        return userResetPwService.resetPassword(userResetPwDTO);
    }

}
