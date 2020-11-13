package com.vsta.entity.voyage;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * An abstraction of a real-life PSA outgoing voyage
 */

@Entity @Data @Table
@NoArgsConstructor @AllArgsConstructor
public class VoyageOut {
    /**
     * Constructs a specified VoyageOut object
     * @param voyageId the auto-generated ID of the voyage, identified by vesselName and voyageNum
     * @param departDt expected date & time where voyage will depart
     */
    @Id private int id;
    private String departDt;
}
