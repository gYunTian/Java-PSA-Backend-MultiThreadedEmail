package com.vsta.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * Details input by user at Reset password.
 */

public class UserResetPwDTO implements UserManagementDTO {

    /**
     * Email specified by the User.
     */
    final private String email;
    
    /**
     * Token specified by the User.
     */
    @NotBlank(message = "Token must be provided")
    final private String token;

    /**
     * New password the user wants to change to.
     */
    @NotBlank(message = "Password must be provided")
    final private String password;

    /**
     * Constructs object for Reset password functionality.
     * @param email Email specified by the User.
     * @param token Password reset token specified by the User.
     * @param password Chosen password the user wants to change to.
     */
    @JsonCreator
    public UserResetPwDTO(
            @JsonProperty("email") String email,
            @JsonProperty("token") String token,
            @JsonProperty("newPassword") String password) {
        this.email = email;
        this.token = token;
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
     * Gets the token specified by the User.
     * @return  This User's specified token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the new password the User wants to change to.
     * @return  This User's specified new password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets all the fields specified by the User.
     * @return  HashMap containing the user-
     *          specified email, token and
     *          new password.
     */
    public Map<String,String> getAll() {
        Map<String,String> attributes = new HashMap<>();
        attributes.put("email", email);
        attributes.put("token", token);
        attributes.put("new password", password);
        return attributes;
    }

}
