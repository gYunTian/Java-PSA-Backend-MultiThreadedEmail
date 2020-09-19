package com.portnet.entity.voyage;

import lombok.*;
import javax.persistence.*;

@Entity @Data @Table
@NoArgsConstructor @AllArgsConstructor
public class VoyageId {
    /**
     * Constructs a specified VoyageId object
     * @param id the auto-generated ID of the voyage, identified by vesselName and voyageNum
     * @param vesselName vessel's short name
     * @param voyageNum incoming or outgoing voyage number
     */
    @Id private int id;
    private String vesselName;
    private String voyageNum;
}
