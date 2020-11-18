package com.vsta.dao;

import com.vsta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "user" table.
 * Used to perform various operations on the database
 * including retrieval and modification.
 */

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    /**
     * Custom method to find User with specified email.
     * @param email Email used by the User at registration.
     * @return User object of indicated email.
     */
    User findByEmail(String email);

}
