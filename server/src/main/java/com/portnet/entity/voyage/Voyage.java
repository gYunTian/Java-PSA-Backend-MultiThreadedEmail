package com.portnet.entity.voyage;

import lombok.*;

import javax.persistence.*;

/**
 * An abstraction of a real-life PSA voyage
 */

@Entity @Data @Table(name="voyage")
@NoArgsConstructor @AllArgsConstructor
public class Voyage {
    /**
     * Constructs a specified Voyage object
     * @param voyageId which identifies a unique voyage (vesselName and voyageNum)
     * @param berthNum location where voyage will be berthed
     * @param status whether the voyage has berthed or otherwise
     * @param changeCount number of times the timing changed
     * */
    @Id private int voyageId;
    private String berthNum;
    private String status;
    private int changeCount;
}
