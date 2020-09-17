package com.portnet.entity.voyage;

import lombok.*;
import javax.persistence.*;

@Entity @Data @Table(name="voyage_id")
@NoArgsConstructor @AllArgsConstructor
public class VoyageId {
    /**
     * Constructs a specified VoyageId object
     * @param id the auto-generated ID of the Voyage
     * @param vesselName vessel's short name
     * @param voyageNum incoming or outgoing voyage number
     */
    @Id private int id;

    @Column(name = "vessel_name")
    private String vesselName;

    @Column(name = "voyage_number")
    private String voyageNum;

}
