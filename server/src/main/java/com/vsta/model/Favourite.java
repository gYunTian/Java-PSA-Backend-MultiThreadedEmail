package com.vsta.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a voyage that is favourite by a user.
 */
@Entity
@Table(name = "favourite")
public class Favourite {

    /**
     * ID to uniquely identify a Favourite.
     * Auto-generated and auto-incremented
     * by database.
     */
    @Id private int id;

    /**
     * ID that uniquely identifies a User.
     */
    @Column(name = "user_id")
    private int userId;

    /**
     * ID that uniquely identifies a voyage.
     */
    @Column(name = "voyage_id")
    private String voyageId;

    /**
     * Default no argument constructor for Favourite.
     */
    public Favourite() {
    }

    /**
     * Constructs a new Favourite object.
     * The userId and voyageId will be passed in
     * from the client side when user click Favourite
     * @param id ID to uniquely identify a Favourite
     * @param userId ID to uniquely identify a User
     * @param voyageId ID to uniquely identify a Voyage
     */
    public Favourite(int id, int userId, String voyageId) {
        this.id = id;
        this.userId = userId;
        this.voyageId = voyageId;
    }

    /**
     * Gets the ID of the a Favourite.
     * @return This Favourite's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Overwrites the ID of this Favourite.
     * @param id This Favourite's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the User.
     * @return  ID of User who favourited the voyage.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Overwrites the ID of the User.
     * @param userId ID of User who favourited the voyage.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the Voyage.
     * @return  ID of Voyage that User favourited.
     */
    public String getVoyageId() {
        return voyageId;
    }


    /**
     * Custom equals method to account all elements
     * @param object that could be Favourite type or otherwise
     * @return <code>true</code> if  both objects are the same
     *                or have the same userId and voyageId
     *         <p>
     *         <code>false</code> if object is null or not VoyageId type
     *                or both objects have different userId and/or voyageId
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (!(object instanceof Favourite)) return false;
        Favourite favourite = (Favourite) object;
        return userId == favourite.userId &&
                voyageId.equals(favourite.voyageId);
    }

    /**
     * Custom hash code method which will uniquely
     * identify Favourite by userId and voyageId
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, voyageId);
    }

    /**
     * String representation
     * @return String representation of Favourite object
     */
    @Override
    public String toString() {
        return String.format("Favourite [id=%d, userId=%d, voyageId=%s]", id, userId, voyageId);
    }
}
