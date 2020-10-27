package com.portnet.entity.storage;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * An abstraction of a real-life user of the application
 */

@Entity @Data @Table
@NoArgsConstructor @AllArgsConstructor
public class User {

    /**
     * Constructs a specified User object
     * @param id the auto-generated ID of the user
     * @param name the name registered by the user
     * @param email the email registered by the user
     * @param password the encoded password of the user
     * @param token generated token to identify user for password reset requests
     */

    @Id private int id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private String token;

    public void setPassword(String password) {
        if (!password.equals("")) {
            // prevent encode "" else blank pw will end up being treated as not blank
            this.password = new BCryptPasswordEncoder().encode(password);
            return;
        }
        this.password = password;
    }

    public void setToken() {
        this.token = UUID.randomUUID().toString();
    }
}
