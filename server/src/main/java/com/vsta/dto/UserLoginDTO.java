package com.vsta.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * Details input by User at Login.
 */

public class UserLoginDTO implements UserManagementDTO {

    /**
     * The email used by the User at login.
     * This is not nullable and should have a
     * valid format, which are checked at login,
     * but the validation annotations are still
     * in place to enforce this.
     */
    @NotBlank(message = "Email must be provided")
    @Email(message = "Email should be valid")
    final private String email;

    /**
     * The raw password set by the User.
     * This is not nullable, which is checked
     * at login, but the validation annotation
     * is still in place to enforce this.
     * This has not been hashed yet.
     */
    @NotBlank(message = "Password must be provided")
    final private String password;

    /**
     * Constructs a new User object at login.
     * @param email Email specified by the User.
     * @param password Password specified by the User.
     */
    @JsonCreator
    public UserLoginDTO(@JsonProperty("email") String email,
                        @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the email specified by the User.
     * @return  This User's specified email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password specified by the User.
     * @return  This User's specified password.
     *          Password has not been hashed.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets all the fields specified by the User.
     * @return  HashMap containing the user-
     *          specified email and password.
     */
    public Map<String,String> getAll() {
        Map<String,String> attributes = new HashMap<>();
        attributes.put("email", email);
        attributes.put("password", password);
        return attributes;
    }

}
