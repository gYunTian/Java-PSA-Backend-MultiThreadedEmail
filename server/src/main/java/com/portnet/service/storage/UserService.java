package com.portnet.service.storage;

import com.portnet.dao.storage.UserDao;
import com.portnet.entity.storage.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Add User to database
     * @param user object
     */
    public void saveUser(User user) {
        userDao.save(user);
    }

    /**
     * Add Users in array to database
     * @param users object
     */
    public void saveUsers(List<User> users) {
        userDao.saveAll(users);
    }


    /**
     * Get all Users in database
     * @return users object
     */
    public List<User> getUsers() {
        return userDao.findAll();
    }

    /**
     * Get User with specified id in database
     * @param id the auto-generated ID of the user
     * @return user object
     */
    public User getUser(int id) {
        return userDao.findById(id).orElse(null);
    }

    /**
     * Get User with specified email in database
     * @param email the email registered by the User
     * @return user object
     */
    public User getUser(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * Update User with same id from database
     */
    public void updateUser(User user) {
        User existingUser = getUser(user.getId());

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        userDao.save(existingUser);
    }

}
