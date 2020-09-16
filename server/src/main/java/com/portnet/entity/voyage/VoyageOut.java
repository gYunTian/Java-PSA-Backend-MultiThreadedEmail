package com.portnet.entity.voyage;

import com.portnet.utility.VoyageId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * An abstraction of a real-life PSA outgoing voyage
 */

@Entity @Data @Table(name="voyage_out")
@NoArgsConstructor @AllArgsConstructor
public class VoyageOut implements Serializable {
    /**
     * Constructs a specified VoyageOut object
     * @param voyageId which identifies a unique voyage (vesselName and voyageNum)
     * @param departDt expected date & time where voyage will depart
     * */
//    @Id @EmbeddedId
//    private VoyageId voyageId;

    @Id
    private int id;

    @Column(name = "etdDt")
    private String departDt;
}
