package com.portnet.dao.storage;

import com.portnet.entity.storage.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "user" table to perform various operations
 */

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    /**
     * Additional custom method to find User with specified email
     * @param email the email registered by the User
     */
    User findByEmail(String email);
    User findByToken(String token);
}
