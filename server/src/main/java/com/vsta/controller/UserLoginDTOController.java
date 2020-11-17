package com.vsta.controller;

import com.vsta.dto.UserLoginDTO;
import com.vsta.service.UserLoginDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST APIs using service methods for User Login
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserLoginDTOController {

    @Autowired
    UserLoginDTOService userLoginDTOService;

    /**
     * Check if User login details are valid
     * @param userLoginDTO Email and password specified by user
     * @return  ResponseEntity with a status code and message
     *          indicating if user login successful
     */
    @PostMapping(value = "/loginUser")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return userLoginDTOService.verifyUser(userLoginDTO);
    }

}
