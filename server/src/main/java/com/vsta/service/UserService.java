package com.vsta.service;

import com.vsta.dao.UserDAO;
import com.vsta.utility.domain.DomainService;
import com.vsta.model.User;
import com.vsta.utility.MailUtil;
import com.vsta.utility.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
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

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private StandardEnvironment environment;

    // Response messages
    final String errorMsgPrefix = "Registration unsuccessful - ";

    final String duplicateEmailMsg = errorMsgPrefix + "email already exists";
    final String unacceptedDomainMsg = errorMsgPrefix + "email domain not accepted";

    final String nonExistentEmailMsg = errorMsgPrefix + "email not registered";

    final String successMsg = "Registration successful";

    /**
     * Check if registered email passes validity checks.
     * @param email Email used by the User at registration.
     * @return  ResponseEntity with an error message and
     *          400 status code if invalid, else null.
     */
    private ResponseEntity<String> invalidRegistrationResponse(String email) {

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
     * Add User to database if data passes validity checks.
     * @param user User object at registration with plain password.
     * @return  ResponseEntity with a status code and message
     *          indicating if user is added successfully.
     */
    public ResponseEntity<String> saveUser(User user) {
        String email = user.getEmail();

        // if empty, don't allow perform other checks
        ResponseEntity<String> emptyFieldsResponse = ValidationUtil.emptyStringResponse(email, "email", errorMsgPrefix);
        if (emptyFieldsResponse != null) {
            return emptyFieldsResponse;
        }

        // not empty, proceed
        // if invalid, don't allow user to register
        ResponseEntity<String> invalidResponse = invalidRegistrationResponse(email);
        if (invalidResponse != null) {
            return invalidResponse;
        }

        // since passed checks, save into database with hashed password
        user.setHashedPassword(user.getPassword());
        userDao.save(user);

        // retrieve after added to get ID for response message, else defaults to 0
        User savedUser = getUserByEmail(email);

        String responseMessage = successMsg + ", user has ID: " + savedUser.getId();
        return ResponseEntity.ok(responseMessage);
    }

    /**
     * Get User with specified email in database.
     * @param email Email of a User.
     * @return User object if can be found, else null.
     */
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * Get User with specified id in database.
     * @param id ID to uniquely identify a User.
     * @return User object if can be found, else null.
     */
    public User getUserById(int id) {
        return userDao.findById(id).orElse(null);
    }

    /**
     * Update User with same ID from database.
     * @param user User object with updated details.
     */
    public void updateUser(User user) {
        User existingUser = getUserById(user.getId());

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setHashedPassword(user.getPassword());
        existingUser.setToken(user.getToken());

        userDao.save(existingUser);
    }

    /**
     * Generate and save token of User who successfully
     * requested the password reset into database.
     * @param user User object of requester.
     */
    public void addToken(User user) {
        user.setToken();
        updateUser(user);
    }

    /**
     * Specific method to send mail to User.
     * @param email User Email specified.
     * @return  ResponseEntity with a status code and message
     *          indicating successful sending of email.
     */
    public ResponseEntity<String> resetPasswordRequest(String email) {

        // if empty, don't allow perform other checks
        ResponseEntity<String> emptyFieldsResponse = ValidationUtil.emptyStringResponse(email, "email", errorMsgPrefix);
        if (emptyFieldsResponse != null) {
            return emptyFieldsResponse;
        }

        // not empty, proceed
        User existingUser = getUserByEmail(email);

        if (existingUser == null) {
            return new ResponseEntity<>(nonExistentEmailMsg, HttpStatus.BAD_REQUEST);
        }

        // since passed checks, request accepted
        addToken(existingUser);

        // construct email
        String subject = "Vsta Account Password Reset";

        String link = environment.getProperty("url") + "?email=" + email + "&token=" + existingUser.getToken();
        String content = "We received a request to reset the password of your Vsta account.\n\n" +
                "You may use the following link to change your password:\n" +
                "" + link + "\n\n" +
                "If you did not make such a request, kindly ignore this email.";

        // send email
        return mailUtil.sendEmail(existingUser, subject, content);
    }

}
