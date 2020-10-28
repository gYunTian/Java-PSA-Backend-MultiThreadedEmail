package com.portnet.entity.voyagebyuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Storage for the voyage subscribed by a user
 */
@Entity @Data @Table
@NoArgsConstructor @AllArgsConstructor
public class VoyageSub implements Serializable {
    /**
     * Constructs a specified VoyageSub object
     * @param userId the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage, identified by record
     */
    @Id private int id;
    private int userId;
    private String voyageId;
}
