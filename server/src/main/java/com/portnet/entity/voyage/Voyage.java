package com.portnet.entity.voyage;

import com.portnet.utility.VoyageId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * An abstraction of a real-life PSA voyage
 */

@Entity @Data @Table(name="voyage")
@NoArgsConstructor @AllArgsConstructor
public class Voyage implements Serializable {
    /**
     * Constructs a specified Voyage object
     * @param voyageId which identifies a unique voyage (vesselName and voyageNum)
     * @param berthNum location where voyage will be berthed
     * @param status whether the voyage has berthed or otherwise
     * @param changeCount number of times the timing changed
     * */

    @EmbeddedId
    private VoyageId voyageId;

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
