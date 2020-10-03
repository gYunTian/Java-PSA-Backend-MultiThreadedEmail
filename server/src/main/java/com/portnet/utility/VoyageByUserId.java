package com.portnet.utility;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Primary key type for VoyageFav & VoyageSub
 */

@Data
@NoArgsConstructor @AllArgsConstructor
public class VoyageByUserId implements Serializable {
    /**
     * Constructs a specified Voyage primary key
     * @param userId the auto-generated ID of the user
     * @param voyageId the auto-generated ID of the voyage, identified by vesselName and voyageNum
     */
    private int userId;
    private String voyageId;
    // todo: link to tables

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
        if (!(object instanceof VoyageByUserId)) return false;
        VoyageByUserId voyagebyUserId = (VoyageByUserId) object;
        return userId == voyagebyUserId.userId &&
                voyageId == voyagebyUserId.voyageId;
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
