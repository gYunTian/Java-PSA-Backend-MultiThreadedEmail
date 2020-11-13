package com.vsta.entity.voyagebyuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Storage for the voyage subscribed by a user
 */
@Entity @Data @Table(name = "voyage_sub")
@NoArgsConstructor @AllArgsConstructor
public class VoyageSub implements Serializable {
    /**
     * Constructs a specified VoyageSub object
     * @param userId the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage, identified by record
     */
    @Id private int id;
    @Column(name = "user_id")
    private int userId;

    @Column(name = "voyage_id")
    private String voyageId;


    /**
     * Custom equals method to account all elements
     * @param object that could be VoyageSub type or otherwise
     * @return true:  both objects are the same
     *                or have the same userId and voyageId
     *         false: object is null or not VoyageId type
     *                or both objects have different userId and/or voyageId
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (!(object instanceof VoyageSub)) return false;
        VoyageSub voyageSub = (VoyageSub) object;
        return userId == voyageSub.userId &&
                voyageId.equals(voyageSub.voyageId);
    }

    /**
     * Custom hash code method which represents all elements
     * @return int representing hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, voyageId);
    }

    /**
     * String representation
     * @return String representation of object
     */
    @Override
    public String toString() {
        return String.format("VoyageSub [id=%d, userId=%d, voyageId=%s]", id, userId, voyageId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(String voyageId) {
        this.voyageId = voyageId;
    }
}
