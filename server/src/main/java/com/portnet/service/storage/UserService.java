package com.portnet.service.storage;

import com.portnet.dao.storage.UserDao;
import com.portnet.entity.dto.LoginDTO;
import com.portnet.entity.dto.NewPasswordDTO;
import com.portnet.entity.storage.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


/**
 * Service tasks that use DAO methods
 * - retrieve and modify database
 * - useful for REST APIs for User
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DomainService domainService;

    /**
     * Add User to database if data passes validity checks
     * @param user object
     * @return message indicating if user registration successful
     */
    public ResponseEntity<String> saveUser(User user) {
        // retrieve input values
//        String name = user.getName();
        String email = user.getEmail();
//        String pwd = user.getPassword();

//        // input length validity
//        if (name.length()>32 || email.length()>32 || pwd.length()>32) {
//            return new ResponseEntity<>(
//                    "Registration unsuccessful - inputs too long, keep within 32 characters",
//                    HttpStatus.BAD_REQUEST);
//        }

        // email validity
        if (getUserByEmail(email) != null) {
            return new ResponseEntity<>(
                    "Registration unsuccessful - email already exists",
                    HttpStatus.BAD_REQUEST);
        } else if (!domainService.domainAccepted(email)) {
            return new ResponseEntity<>(
                    "Registration unsuccessful - email domain not accepted",
                    HttpStatus.BAD_REQUEST);
        }

        // passed checks
        userDao.save(user);
        return ResponseEntity.ok("Registration successful");
    }

    /**
     * Allow User to login if data passes validity checks
     * @return message indicating if user registration successful
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
        return ResponseEntity.ok("Login successful - details valid");
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
        existingUser.setPassword(user.getPassword());
        existingUser.setToken(user.getToken());

        userDao.save(existingUser);
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
     * Update password & remove reset token of specified User
     * @param user object that has been verified to be correct
     * @param password the new verified password chosen by the user
     */
    public ResponseEntity<String> changePassword(User user, String password) {
        user.setPassword(password);

        // remove token
        user.setToken(null);
        updateUser(user);

        return ResponseEntity.ok("Password change successful");
    }

    /**
     * Allow User to login if data passes validity checks
     * @param newPasswordDTO containing user object, and the old password & new password input by user
     * @return message indicating if change password successful
     */
    public ResponseEntity<String> changePasswordController(NewPasswordDTO newPasswordDTO) {
        User user = newPasswordDTO.getUser();
        String oldPasswordGiven = newPasswordDTO.getIdentifier();

        if (!user.getPassword().equals(oldPasswordGiven)) {
            return new ResponseEntity<>(
                    "Change Password unsuccessful - wrong password",
                    HttpStatus.BAD_REQUEST);
        }
        return changePassword(user, newPasswordDTO.getNewPassword());
    }

    /**
     * Allow User to login if data passes validity checks
     * @param newPasswordDTO containing the token & new password input by user
     * @return message indicating if change password successful
     */
    public ResponseEntity<String> resetPasswordController(NewPasswordDTO newPasswordDTO) {
        String token = newPasswordDTO.getIdentifier();
        User user = getUserByToken(token);

        if (user == null) {
            return new ResponseEntity<>(
                    "Change Password unsuccessful - wrong token",
                    HttpStatus.BAD_REQUEST);
        }
        return changePassword(user, newPasswordDTO.getNewPassword());
    }

    /**
     * Specific method to send mail to user for respective purposes
     * @param email the email registered by the User
     * @param attrs to store & bring email content to the next view
     * @return redirects to mail which returns status message on successful sending of email
     */
    public RedirectView changePasswordRequest(String email, RedirectAttributes attrs) {
        try {
            User user = getUserByEmail(email);  // if null, catch exception
            
            // if proceed, means user is not null so request accepted
            addToken(user); // generate password reset token for email body & save into database

            // For Redirection to mail
            attrs.addFlashAttribute("user", user);
            attrs.addFlashAttribute("type", "changePasswordRequest");

        } catch (NullPointerException e) {
            attrs.addFlashAttribute("user", null);
            attrs.addFlashAttribute("type", "rejectRequest");
        }

        return new RedirectView("sendEmail");
    }


    /**
     * Remove specified User from database
     * @param user object that requested deactivation of account
     */
    public ResponseEntity<String> deleteUser(User user) {
        userDao.delete(user);
        return ResponseEntity.ok("User deleted successful");
    }
}
