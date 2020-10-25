package com.portnet.entity.storage;

import lombok.*;
import javax.persistence.*;
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
     */
    @Id private int id;
    private String name;
    private String email;
    private String password;
    private String token;

    public void setPassword(String password) {
        this.password = password;
        // passwordEncoder.encode(password));
    }

    public void setToken() {
        this.token = UUID.randomUUID().toString();
    }
}
