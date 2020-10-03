package com.portnet.entity.voyage;

import com.portnet.utility.VoyageByUserId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Storage for the voyage favorited by a user
 */

@Entity @Data @Table
@NoArgsConstructor @AllArgsConstructor
@IdClass(VoyageByUserId.class)
public class VoyageFav implements Serializable {
    /**
     * Constructs a specified VoyageFav object
     * @param userId the auto-generated ID of the user
     * @param voyageId the auto-generated ID of the voyage, identified by vesselName and voyageNum
     */
    @Id private int userId;
    @Id private int voyageId;
}
