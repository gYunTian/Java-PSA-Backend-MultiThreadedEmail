package com.portnet.utility;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Primary key type for Voyage
 */

@Embeddable @Data
@NoArgsConstructor @AllArgsConstructor
public class VoyageId implements Serializable {
    /**
     * Constructs a specified Voyage primary key
     * @param vesselName vessel's short name
     * @param voyageNum incoming or outgoing voyage number
     */
    @Column(name = "vessel_name", nullable = false)
    private String vesselName;

    @Column(name = "voyage_number", nullable = false)
    private String voyageNum;

    /**
     * Custom equals method to account all elements
     * @param object that could be VoyageId type or otherwise
     * @return true:  both objects are the same
     *                or have the same vesselName and voyageNum
     *         false: object is null or not VoyageId type
     *                or both objects have different vesselName and/or voyageNum
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof VoyageId)) return false;
        VoyageId voyageId = (VoyageId) object;
        return vesselName.equals(voyageId.vesselName) &&
                voyageNum.equals(voyageId.voyageNum);
    }

    /**
     * Custom get method which accounts all elements
     * @return int representing hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(vesselName, voyageNum);
    }
}
