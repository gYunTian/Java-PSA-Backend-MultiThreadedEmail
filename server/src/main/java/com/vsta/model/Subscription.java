package com.vsta.model;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Represents a subscription by a user for a voyage.
 */

@Entity
@NoArgsConstructor
@Table(name = "subscription")
public class Subscription implements IUserSavedVessel {

    /**
     * ID to uniquely identify a Subscription.
     * Auto-generated and auto-incremented by database.
     */
    @Id 
    private int id;

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
     * Constructs a new Subscription object.
     * The userId and voyageId will be passed in from the client side
     * when user click Subscribe.
     * @param id ID to uniquely identify a Subscription
     * @param userId ID to uniquely identify a User
     * @param voyageId ID to uniquely identify a Voyage
     */
    public Subscription(int id, int userId, String voyageId) {
        this.id = id;
        this.userId = userId;
        this.voyageId = voyageId;
    }

    /**
     * Gets the ID of the a Subscription.
     * @return  This Subscription's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the ID of the User.
     * @return ID of User who subscribed to the voyage.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Overwrites the ID of the User.
     * @param userId ID of User who subscribed to the voyage.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the Voyage.
     * @return  ID of Voyage that User subscribed to.
     */
    public String getVoyageId() {
        return voyageId;
    }

    /**
     * Custom equals method to account all elements.
     * @param object that could be Subscription type or otherwise.
     * @return <code>true</code>    if both objects are the same
     *                              or have the same userId and voyageId
     *         <p>
     *         <code>false</code>   if object is null or not VoyageId type
     *                              or both objects have different userId
     *                              and/or voyageId
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (!(object instanceof Subscription)) return false;
        Subscription subscription = (Subscription) object;
        return userId == subscription.userId &&
                voyageId.equals(subscription.voyageId);
    }

    /**
     * Custom hash code method which will uniquely
     * identify Subscription by userId and voyageId
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, voyageId);
    }

    /**
     * Override toString method to encapsulate all elements in string representation.
     * @return String representation of Subscription object.
     */
    @Override
    public String toString() {
        return String.format("Subscription [id=%d, userId=%d, voyageId=%s]", id, userId, voyageId);
    }

}
