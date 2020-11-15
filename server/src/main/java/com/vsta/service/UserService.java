package com.vsta.service;

import com.vsta.dao.UserDAO;
import com.vsta.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * User Service tasks that use DAO methods
 * and used for REST APIs for User Object.
 */

@Service
public class UserService {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private DomainService domainService;


    final String errorMsgPrefix = "Registration unsuccessful - ";

    final String duplicateEmailMsg = errorMsgPrefix + "email already exists";
    final String unacceptedDomainMsg = errorMsgPrefix + "email domain not accepted";

    /**
     * Add User to database if data passes validity checks
     * @param user User object to be added into database
     * @return  ResponseEntity with the given status code and message
     *          indicating if user is added successfully
     */
    public ResponseEntity<String> saveUser(User user) {
        String email = user.getEmail();

        ResponseEntity<String> invalidEmailResult = invalidRegisteredEmailResult(email);
        if (invalidEmailResult != null) return invalidEmailResult;

        // passed checks
        user.setHashedPassword(user.getPassword());
        userDao.save(user);

        // save to get ID
        User savedUser = getUserByEmail(email);
        return ResponseEntity.ok("Registration successful, user has ID: " + savedUser.getId());
    }


    /**
     * Check if registered email passes validity checks
     * @param email Email used by the User at registration.
     * @return  ResponseEntity with an error message and Bad Request status code if user is not null,
     *          else return null
     */
    public ResponseEntity<String> invalidRegisteredEmailResult(String email) {
        User existingUser = getUserByEmail(email);
        if (existingUser != null) {
            return new ResponseEntity<>(duplicateEmailMsg, HttpStatus.BAD_REQUEST);
        }
        if (!domainService.domainAccepted(email)) {
            return new ResponseEntity<>(unacceptedDomainMsg, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    /**
     * Check if User object is null
     * @param existingUser existing User object that is to be verified
     * @param errorMsg Error message to return if needed
     * @return  ResponseEntity with the given error message and Bad Request status code if user is null,
     *          else return null
     */
    public ResponseEntity<String> nullUserResult(User existingUser, String errorMsg) {

        if (existingUser == null) {
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        return null;
    }


    /**
     * Get User with specified email in database
     * @param email Email used by the User at registration.
     * @return User object (null if not found)
     */
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * Get User with specified id in database
     * @param id ID to uniquely identify a User.
     * @return User object (null if not found)
     */
    public User getUserById(int id) {
        return userDao.findById(id).orElse(null);
    }

    /**
     * Update User with same ID from database
     * @param user User object with updated details
     */
    public void updateUser(User user) {
        User existingUser = getUserById(user.getId());

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setHashedPassword(user.getPassword());
        existingUser.setToken(user.getToken());

        userDao.save(existingUser);
    }

}
