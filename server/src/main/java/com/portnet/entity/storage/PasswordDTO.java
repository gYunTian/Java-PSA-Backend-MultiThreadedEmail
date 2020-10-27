package com.portnet.entity.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordDTO {
    private User user;

    @NotBlank(message = "Identifier is mandatory")
    private String identifier;  // old password or token input

    @NotBlank(message = "Password is mandatory")
    private String newPassword;
    // for now, assumes already validated to be not null else can hack sys

    /**
     * Constructs object for Reset password functionality
     * @param identifier token
     * @param newPassword chosen password the user wants to change to
     */
    @JsonCreator
    public PasswordDTO(@JsonProperty("identifier") String identifier,
                       @JsonProperty("new_password") String newPassword) {
        this.identifier = identifier;
        this.newPassword = newPassword;
    }

    /**
     * Constructs object for Change password functionality
     * @param identifier old password of the user
     * @param newPassword chosen password the user wants to change to
     */
    @JsonCreator
    public PasswordDTO(@JsonProperty("user") User user,
                       @JsonProperty("identifier") String identifier,
                       @JsonProperty("new_password") String newPassword) {
        this.user = user;
        this.identifier = identifier;
        this.newPassword = newPassword;
    }

}
