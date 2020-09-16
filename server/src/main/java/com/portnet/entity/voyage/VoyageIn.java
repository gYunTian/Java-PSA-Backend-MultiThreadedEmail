package com.portnet.entity.voyage;

import com.portnet.utility.VoyageId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * An abstraction of a real-life PSA incoming voyage
 */

@Entity @Data @Table(name="voyage_in")
@NoArgsConstructor @AllArgsConstructor
public class VoyageIn implements Serializable {
    /**
     * Constructs a specified VoyageIn object
     * @param voyageId which identifies a unique voyage (vesselName and voyageNum)
     * @param berthDt most updated date & time where voyage will be berthed
     * @param firstBerthDt initially set date & time where voyage will be berthed
     * */
//    @EmbeddedId
//    private VoyageId voyageId;

    @Id
    private int id;

    @Column(name = "btrDt")
    private String berthDt;

    @Column(name = "firstBtrDt")
    private String firstBerthDt;
}
