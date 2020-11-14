package com.vsta.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Storage for the voyage favorited by a user
 */
@Entity @Data
@Table(name = "voyage_fav")
@NoArgsConstructor @AllArgsConstructor
public class VoyageFav {
    /**
     * Constructs a specified VoyageFav object
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
     * @param object that could be VoyageFav type or otherwise
     * @return true:  both objects are the same
     *                or have the same userId and voyageId
     *         false: object is null or not VoyageId type
     *                or both objects have different userId and/or voyageId
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (!(object instanceof VoyageFav)) return false;
        VoyageFav voyageFav = (VoyageFav) object;
        return userId == voyageFav.userId &&
                voyageId.equals(voyageFav.voyageId);
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
        return String.format("VoyageFav [id=%d, userId=%d, voyageId=%s]", id, userId, voyageId);
    }

}
