package com.vsta.service.storage;

import com.vsta.dao.storage.UserDao;
import com.vsta.entity.dto.LoginDTO;
import com.vsta.entity.dto.NewPasswordDTO;
import com.vsta.entity.storage.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;


/**
 * Service tasks that use DAO methods
 * - retrieve and modify database
 * - useful for REST APIs for User Object
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DomainService domainService;

    @Autowired
    private MailService mailService;

    /**
     * Add User to database if data passes validity checks
     * @param user object
     * @return ResponseEntity with the given status code and message indicating if user registration successful
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

        user.setPassword(hashPassword(user.getPassword()));
        // passed checks
        userDao.save(user);

        User savedUser = getUserByEmail(email);

        return ResponseEntity.ok("Registration successful, user has ID: " + savedUser.getId());
    }

    /**
     * Allow User to login if data passes validity checks
     * @param loginDTO containing the email and password of user
     * @return ResponseEntity with the given status code and message indicating if user registration successful
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
     * Get all Users in database
     * @return users object (null if not found)
     */
    public List<User> getUsers() {
        return userDao.findAll();
    }

    /**
     * Get User with specified id in database
     * @param id the auto-generated ID of the user
     * @return user object (null if not found)
     */
    public User getUserById(int id) {
        return userDao.findById(id).orElse(null);
    }

    /**
     * Get User with specified email in database
     * @param email the email registered by the User
     * @return user object (null if not found)
     */
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * Get User with specified email in database
     * @param token the verification code generated for the user to reset password
     * @return user object (null if not found)
     */
    public User getUserByToken(String token) {
        return userDao.findByToken(token);
    }


    /**
     * Update User with same id from database (helper method)
     * @param user object with updated details
     */
    public void updateUser(User user) {
        User existingUser = getUserById(user.getId());

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(hashPassword(user.getPassword()));
        existingUser.setToken(user.getToken());

        userDao.save(existingUser);
    }

    /**
     * Hash the password that is pass in
     * @param password password to be hashed
     * @return password that is hashed, if password is blank, empty string will be returned
     */
    public String hashPassword(String password) {
        if (!password.equals("")) { // prevent encode "" else blank pw will end up being treated as not blank
            return new BCryptPasswordEncoder().encode(password);
        }
        return "";
    }


    /**
     * Add token to specified User
     * @param user object representing the requester
     */
    public void addToken(User user) {
        user.setToken();
        updateUser(user);
    }

    /**
     * Update password and remove reset token of specified User
     * @param user object that has been verified to be correct
     * @param password the new verified password chosen by the user
     * @return ResponseEntity with the given status code and message indicating if password updated
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
     * @param newPasswordDTO containing the token and new password input by user
     * @return ResponseEntity with the given status code and message indicating if change password successful
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
     * Specific method to send mail to user for respective purposes
     * @param email the email registered by the User
     * @return ResponseEntity with the given status code and message indicating successful sending of email
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

    /**
     * Remove specified User from database
     * @param user object that requested deactivation of account
     * @return ResponseEntity with the given status code and message indicating if user is deleted successfully
     */
    public ResponseEntity<String> deleteUser(User user) {
        userDao.delete(user);
        return ResponseEntity.ok("User deleted successful");
    }
}
