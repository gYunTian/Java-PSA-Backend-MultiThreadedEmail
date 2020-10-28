package com.portnet.entity.voyagebyuser;

import lombok.*;

import javax.persistence.*;

/**
 * Storage for the voyage favorited by a user
 */
@Entity @Data @Table
@NoArgsConstructor @AllArgsConstructor
public class VoyageFav {
    /**
     * Constructs a specified VoyageFav object
     * @param userId the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage, identified by record
     */
    @Id private int id;
    private int userId;
    private String voyageId;
}
