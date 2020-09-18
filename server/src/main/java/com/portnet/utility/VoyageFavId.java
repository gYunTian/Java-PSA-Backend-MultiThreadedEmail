package com.portnet.utility;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Primary key type for VoyageFav
 */

@Embeddable @Data
@NoArgsConstructor @AllArgsConstructor
public class VoyageFavId implements Serializable {
    /**
     * Constructs a specified Voyage primary key
     * @param userId the auto-generated ID of the user
     * @param voyageId the auto-generated ID of the voyage, identified by vesselName and voyageNum
     */
    private int userId;
    private String voyageId;

    /**
     * Custom equals method to account all elements
     * @param object that could be VoyageId type or otherwise
     * @return true:  both objects are the same
     *                or have the same userId and voyageId
     *         false: object is null or not VoyageId type
     *                or both objects have different userId and/or voyageId
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof VoyageFavId)) return false;
        VoyageFavId voyageFavId = (VoyageFavId) object;
        return userId == voyageFavId.userId &&
                voyageId == voyageFavId.voyageId;
    }

    /**
     * Custom get method which accounts all elements
     * @return int representing hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, voyageId);
    }
}
