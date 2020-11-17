package com.vsta.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a user account of the application,
 * with the information provided by the user.
 * The user could be either registered or
 * a registering user.
 */

@Entity
@Table(name = "user")
public class User {

    /**
     * The ID to uniquely identify a User.
     * Auto-generated and auto-incremented
     * by database.
     */
    @Id
    private int id;

    /**
     * The name to identify the User.
     * This can be given at registration
     * or derived from the email username
     * if otherwise.
     */
    private String name;

    /**
     * The email used by the User at registration.
     * This is not nullable and should have a valid
     * format, which are checked at registration,
     * but the validation annotations are still
     * in place to enforce this before storing
     * into the database.
     */
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * The modifiable password set by the User.
     * This is not nullable, which is checked at
     * registration, but the validation annotation
     * is still in place to enforce this before
     * storing into the database.
     * This should also be hashed for security reasons.
     */
    @NotBlank(message = "Password is mandatory")
    private String password;

    /**
     * The generated token to verify a
     * User for password reset requests.
     * This field is to be a random UUID
     * but is null at registration and
     * after successful password reset,
     */
    private String token;


    /**
     * Default no argument constructor for User.
     */
    public User() {
    }
    /**
     * Constructs a new User object.
     * This is when name, email and password
     * are specified at registration.
     * @param id ID to uniquely identify a User.
     * @param name The name to identify the User.
     * @param email Email specified by the User at registration.
     * @param password Password specified by the User.
     */
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the ID of the User.
     * @return  This User's ID.
     */
    public int getId() {
        return id;
    }
    /**
     * Overwrites the ID of this User.
     * @param id    This User's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the User.
     * @return  This User's name.
     */
    public String getName() {
        return name;
    }
    /**
     * Overwrites the name of this User.
     * @param name  This User's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the User.
     * @return  This User's email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Overwrites the email of this User.
     * @param email This User's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the User.
     * @return  This User's password.
     *          Password should be hashed,
     *          and has been validated to be
     *          not blank by annotation.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Overwrites the password of this User.
     * @param password  This User's password.
     *                  Password should be hashed
     *                  and has been validated to be
     *                  not blank by annotation.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Custom method to hash the given password then
     * use it to overwrite the password of this user.
     * @param   password This User's password.
     *                   Password can be plaintext,
     *                   and has been validated to be
     *                   not blank by annotation.
     *                   It is vital to be not blank
     *                   as otherwise it will be
     *                   encoded as well, which is
     *                   misleading and exposes the
     *                   account to security issues.
     */
    public void setHashedPassword(String password) {
        setPassword(new BCryptPasswordEncoder().encode(password));
    }

    /**
     * Gets the password reset token of the User.
     * @return This User's token.
     */
    public String getToken() {
        return token;
    }
    /**
     * Overwrites the password reset token of this User.
     * @param token This User's token.
     */
    public void setToken(String token) {
        this.token = token;
    }
    /**
     * Custom method to create and set a random UUID
     * as the token for reset password requests.
     */
    public void setToken() {
        this.token = UUID.randomUUID().toString();
    }


    /**
     * Override equals method to compare two Users.
     * @param object that could be of User type or otherwise
     * @return <code>true</code> if both objects are the same
     *                or have the same id
     *         <p>
     *         <code>false</code> if object is null or not User type
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
     * Override hashCode method to uniquely
     * identify a User using their id.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Override toString method to encapsulate
     * all elements in string representation.
     * @return string representation of User object
     */
    @Override
    public String toString() {
        return String.format(
                "User [id=%d, name=%s, email=%s, password=%s, token=%s]",
                id, name, email, password, token);
    }

}
