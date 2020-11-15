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

    /**
     * Add User to database if data passes validity checks
     * @param user User object to be added into database
     * @return  ResponseEntity with the given status code and message
     *          indicating if user is added successfully
     */
    public ResponseEntity<String> saveUser(User user) {

        // email validity
        String email = user.getEmail();
        if (getUserByEmail(email) != null) {
            return new ResponseEntity<>(
                    "Registration unsuccessful - email already exists",
                    HttpStatus.BAD_REQUEST);
        } else if (!domainService.domainAccepted(email)) {
            return new ResponseEntity<>(
                    "Registration unsuccessful - email domain not accepted",
                    HttpStatus.BAD_REQUEST);
        }

        user.setHashedPassword(user.getPassword());
        // passed checks
        userDao.save(user);

        User savedUser = getUserByEmail(email);

        return ResponseEntity.ok("Registration successful, user has ID: " + savedUser.getId());
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
     * Get User with specified email in database
     * @param email Email used by the User at registration.
     * @return User object (null if not found)
     */
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * Get User with specified password reset token in database
     * @param token Verification code generated for User to reset password
     * @return User object (null if not found)
     */
    public User getUserByToken(String token) {
        return userDao.findByToken(token);
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
