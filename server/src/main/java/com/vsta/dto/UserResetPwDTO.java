package com.vsta.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Details input by user at
 * Change or Reset password
 */

public class UserResetPwDTO {

    /**
     * Email specified by the User
     */
    final private String email;
    
    /**
     * Token specified by the User
     */
    @NotBlank(message = "Token is mandatory")
    final private String token;

    /**
     * Password the user wants to change to
     */
    @NotBlank(message = "Password is mandatory")
    final private String newPassword;

    /**
     * Constructs object for Reset password functionality
     * @param email Email specified by the User
     * @param token Password reset token specified by the User
     * @param newPassword Chosen password the user wants to change to
     */
    @JsonCreator
    public UserResetPwDTO(
            @JsonProperty("email") String email,
            @JsonProperty("token") String token,
            @JsonProperty("new_password") String newPassword) {
        this.email = email;
        this.token = token;
        this.newPassword = newPassword;
    }

    /**
     * Gets the email specified by the User
     * @return  This User's specified old password or token
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the token specified by the User
     * @return  This User's specified old password or token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the password the User wants to change to
     * @return  This User's specified new password
     */
    public String getNewPassword() {
        return newPassword;
    }

}
