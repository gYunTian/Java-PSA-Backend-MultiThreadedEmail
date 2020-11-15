package com.vsta.controller;

import com.vsta.dto.UserResetPwDTO;
import com.vsta.service.ResetPwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs using service methods for User Reset/Change Password
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserResetPwController {

    @Autowired
    private ResetPwService resetPwService;

    /**
     * Request token for password reset then send email if valid User.
     * @param email Email specified by user.
     * @return  ResponseEntity with the given status code and message
     *          indicating successful sending of email.
     */
    @PostMapping(value = "/resetPasswordRequest")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email) {
        return resetPwService.resetPasswordRequest(email);
    }
    /**
     * Update password of password reset requester.
     * @param userResetPwDTO Token and reset password specified by user.
     * @return  ResponseEntity with the given status code and message
     *          indicating if password change successful.
     */
    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody UserResetPwDTO userResetPwDTO) {
        return resetPwService.resetPasswordController(userResetPwDTO);
    }

}
