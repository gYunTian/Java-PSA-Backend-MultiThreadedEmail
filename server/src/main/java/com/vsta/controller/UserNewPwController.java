package com.vsta.controller;

import com.vsta.dto.UserNewPwDTO;
import com.vsta.service.NewPwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs using service methods for
 * User Reset/Change Password
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserNewPwController {

    @Autowired
    private NewPwService newPwService;

    /**
     * Request token for password reset then send email if valid User.
     * @param email Email specified by user.
     * @return  ResponseEntity with the given status code and message
     *          indicating successful sending of email.
     */
    @PostMapping(value = "/resetPasswordRequest")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email) {
        return newPwService.resetPasswordRequest(email);
    }
    /**
     * Update password of password change requester.
     * @param userNewPwDTO Token and new password specified by user.
     * @return  ResponseEntity with the given status code and message
     *          indicating if password change successful.
     */
    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody UserNewPwDTO userNewPwDTO) {
        return newPwService.resetPasswordController(userNewPwDTO);
    }

//    /**
//     * Update password of password change requester
//     * @param userNewPwDTO Old and new password specified by user
//     * @return  ResponseEntity with the given status code and message
//     *          indicating if password change successful.
//     */
//    @PutMapping("/changePassword")
//    public ResponseEntity<String> changePassword(@RequestBody UserNewPwDTO userNewPwDTO) {
//        return newPwService.changePasswordController(userNewPwDTO);
//    }

}
