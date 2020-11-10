package com.portnet.entity.storage;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
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


    /**
     * Custom setPassword method to encode passwords
     */
    public void setPassword(String password) {
        if (!password.equals("")) { // prevent encode "" else blank pw will end up being treated as not blank
            this.password = new BCryptPasswordEncoder().encode(password);
            return;
        }
        this.password = password;
    }

    /**
     * Custom setToken method to generate random token for new password requests
     */
    public void setToken() {
        this.token = UUID.randomUUID().toString();
    }


    /**
     * Override equals method to account all elements
     * @param object that could be VoyageSub type or otherwise
     * @return true:  both objects are the same
     *                or have the same id
     *         false: object is null or not User type
     *                or both objects have different id
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (!(object instanceof User)) return false;
        User user = (User) object;
        return id == user.id;
    }

    /**
     * Override hashCode method to uniquely represent all elements
     * @return int representing hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Override toString method to encapsulate all elements in string representation
     * @return string representation of object
     */
    @Override
    public String toString() {
        return String.format("User [id=%d, name=%s, email=%s, password=%s, token=%s]", id, name, email, password, token);
    }
}
