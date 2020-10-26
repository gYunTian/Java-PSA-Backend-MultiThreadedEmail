package com.portnet.service.storage;

import com.portnet.dao.storage.UserDao;
import com.portnet.entity.storage.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
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
     * @param user object (null if not found)
     * @return message indicating if user registration successful
     */
    public String saveUser(User user) {
        // retrieve input values
        String name = user.getName();
        String email = user.getEmail();
        String pwd = user.getPassword();

        // input length validity (avoid error when exceed minimum set in database)
        if (name.length()>32 || email.length()>32 || pwd.length()>32) {
            return "Registration unsuccessful - inputs too long, keep within 32 characters";
        }

        // email validity
        if (getUserByEmail(email) != null) {
            return "Registration unsuccessful - email already exists";
        } else if (!domainService.domainAccepted(email)) {
            return "Registration unsuccessful - email domain not accepted";
        }

        // passed checks
        userDao.save(user);
        return "Registration successful";
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
     * Get content of reset password email
     * @param email registered email of the requester
     * @return emailContent containing subject, body and recipient
     */
    public HashMap<String,String> changeUserPasswordRequest(String email) {
        HashMap<String,String> emailContent = new HashMap<>();

        try {
            User user = getUserByEmail(email);  // if null, catch exception
            System.out.println("Request accepted"); // user is not null
            user.setToken();    // generate password reset token

            emailContent.put("subject", "Portnet Account Password Reset");

            String body = "Hi " + user.getName() +",\n\n" +
                    "We received a request to reset the password of your Portnet account.\n\n" +
                    "You may use the following token to change your password:\n" +
                    "" + user.getToken() + "\n\n" +
                    "If you did not make such a request, kindly ignore this email.\n\n\n" +
                    "Thank you!\n" +
                    "G1T9";
            emailContent.put("body", body);

            emailContent.put("recipient", user.getEmail());

        } catch (NullPointerException e) {
            System.out.println("Email is not registered");
        }

        return emailContent;
    }


    /**
     * Update password of User with same id from database
     * @param user object (null if not found)
     * @param password the new verified password chosen by the user
     */
    public void changeUserPassword(User user, String password) {
        user.setPassword(password);
        userDao.save(user);
    }
}
