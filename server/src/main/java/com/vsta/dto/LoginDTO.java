package com.vsta.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Details input by user at Login
 */

@Data
public class LoginDTO {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password is mandatory")
    String password;

    @JsonCreator
    public LoginDTO(@JsonProperty("email") String email,
                    @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}
