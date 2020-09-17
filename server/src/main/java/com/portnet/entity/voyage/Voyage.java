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
     * @param vesselName vessel's short name
     * @param berthNum location where voyage will be berthed
     * @param status whether the voyage has berthed or otherwise
     * @param changeCount number of times the timing changed
     * */

    @Id @Column(name = "voyage_id")
    private int voyageId;

    @Column(name = "berth_number")
    private String berthNum;

    @Column(name = "status")
    private String status;

    @Column(name = "change_count")
    private int changeCount;

    /**
     * Initialise VoyageIn or VoyageOut objects based on voyageNum
     */


}
