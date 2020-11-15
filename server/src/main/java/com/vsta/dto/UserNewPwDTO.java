package com.vsta.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Details input by user at
 * Change or Reset password
 */

@Data
public class UserNewPwDTO {

    /**
     * Old password or token specified by the User
     */
    @NotBlank(message = "Identifier is mandatory")
    private String identifier;

    /**
     * Password the user wants to change to
     */
    @NotBlank(message = "Password is mandatory")
    private String newPassword;

    /**
     * Constructs object for Reset password functionality
     * @param identifier token specified by the User
     * @param newPassword chosen password the user wants to change to
     */
    @JsonCreator
    public UserNewPwDTO(@JsonProperty("identifier") String identifier,
                        @JsonProperty("new_password") String newPassword) {
        this.identifier = identifier;
        this.newPassword = newPassword;
    }

    /**
     * Gets the old password or token specified by the User
     * @return  This User's specified old password or token
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Gets the password the User wants to change to
     * @return  This User's specified new password
     */
    public String getNewPassword() {
        return newPassword;
    }

}
