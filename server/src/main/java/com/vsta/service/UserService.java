package com.vsta.service;

import com.vsta.dao.UserDAO;
import com.vsta.dto.LoginDTO;
import com.vsta.dto.NewPasswordDTO;
import com.vsta.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


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
    private MailService mailService;

    /**
     * Add User to database if data passes validity checks
     * @param user User object of details input at registration
     * @return  ResponseEntity with the given status code and message
     *          indicating if user registration successful
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
     * Allow User to login if data passes validity checks
     * @param loginDTO Email and password specified by user
     * @return  ResponseEntity with the given status code and message
     *          indicating if user registration successful
     */
    public ResponseEntity<String> loginUser(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String givenPassword = loginDTO.getPassword();

        User user = getUserByEmail(email);
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

        return ResponseEntity.ok("User " + user.getId() +", " + user.getName() + "'s Login successful - details valid");
    }

    /**
     * Get User with specified id in database
     * @param id Auto-generated ID of the user
     * @return User object (null if not found)
     */
    public User getUserById(int id) {
        return userDao.findById(id).orElse(null);
    }

    /**
     * Get User with specified email in database
     * @param email User Email specified
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


    /**
     * Generate and save token of User who successfully requested password reset
     * @param user User object representing the requester
     */
    public void addToken(User user) {
        user.setToken();
        updateUser(user);
    }

    /**
     * Update password and remove reset token of specified User
     * @param user User object that has been verified to be correct
     * @param password New password chosen by User
     * @return  ResponseEntity with the given status code and message
     *          indicating if password reset successful
     */
    public ResponseEntity<String> resetPassword(User user, String password) {
        user.setPassword(password);

        // remove token
        user.setToken(null);
        updateUser(user);

        return ResponseEntity.ok("Password reset successful");
    }

//    /**
//     * Allow User to login if data passes validity checks
//     * @param newPasswordDTO containing user object, and the old password & new password input by user
//     * @return message indicating if change password successful
//     */
//    public ResponseEntity<String> changePasswordController(NewPasswordDTO newPasswordDTO) {
//        User user = newPasswordDTO.getUser();
//        String oldPasswordGiven = newPasswordDTO.getIdentifier();
//
//        if (!user.getPassword().equals(oldPasswordGiven)) {
//            return new ResponseEntity<>(
//                    "Change Password unsuccessful - wrong password",
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        user.setPassword(newPasswordDTO.getNewPassword());
//        updateUser(user);
//        return ResponseEntity.ok("Password change successful");
//    }

    /**
     * Allow User to reset password if data passes validity checks
     * @param newPasswordDTO Token and new password input by User
     * @return  ResponseEntity with the given status code and message
     *          indicating if password reset successful
     *          (resetPassword output)
     */
    public ResponseEntity<String> resetPasswordController(NewPasswordDTO newPasswordDTO) {
        String token = newPasswordDTO.getIdentifier();
        User user = getUserByToken(token);

        if (user == null) {
            return new ResponseEntity<>(
                    "Change Password unsuccessful - wrong token",
                    HttpStatus.BAD_REQUEST);
        }
        return resetPassword(user, newPasswordDTO.getNewPassword());
    }

    /**
     * Specific method to send mail to User
     * @param email User Email specified
     * @return  ResponseEntity with the given status code and message
     *          indicating successful sending of email
     */
    public ResponseEntity<String> resetPasswordRequest(String email) {
        try {
            User user = getUserByEmail(email);  // if null, catch exception

            // if proceed, means user is not null so request accepted
            addToken(user); // generate password reset token for email body & save into database
            HashMap<String,String> emailContent = mailService.getEmailContent(user);
            return mailService.sendEmail(emailContent);

        } catch (NullPointerException e) {
            return new ResponseEntity<>(
                "Change Password unsuccessful - email not registered",
                HttpStatus.BAD_REQUEST);
        }
    }

}
