package com.vsta.dto;

import java.util.Map;

/**
 * Abstraction of common DTOs.
 */

public interface UserManagementDTO {

    /**
     * Gets the email specified by the User.
     * @return  This User's specified email.
     */
    String getEmail();

    /**
     * Gets the password specified by the User.
     * @return  This User's specified password.
     *          Password has not been hashed.
     */
    String getPassword();

    /**
     * Gets all the fields specified by the User.
     * @return  HashMap containing all the user-
     *          specified attributes.
     */
    Map<String,String> getAll();

}
