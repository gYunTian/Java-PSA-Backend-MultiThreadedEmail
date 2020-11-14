package com.vsta.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vsta.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Details input by user at Change and Reset password
 */

@Data
public class NewPasswordDTO {
    private User user;

    @NotBlank(message = "Identifier is mandatory")
    private String identifier;  // old password or token input

    @NotBlank(message = "Password is mandatory")
    private String newPassword;

    /**
     * Constructs object for Reset password functionality
     * @param identifier token
     * @param newPassword chosen password the user wants to change to
     */
    @JsonCreator
    public NewPasswordDTO(@JsonProperty("identifier") String identifier,
                          @JsonProperty("new_password") String newPassword) {
        this.identifier = identifier;
        this.newPassword = newPassword;
    }

    /**
     * Constructs object for Change password functionality
     * @param user user object that requested for change of password
     * @param identifier old password of the user
     * @param newPassword chosen password the user wants to change to
     */
    @JsonCreator
    public NewPasswordDTO(@JsonProperty("user") User user,
                          @JsonProperty("identifier") String identifier,
                          @JsonProperty("new_password") String newPassword) {
        this.user = user;
        this.identifier = identifier;
        this.newPassword = newPassword;
    }

}
