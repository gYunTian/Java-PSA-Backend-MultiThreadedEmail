package com.portnet.entity.voyage;

import com.portnet.utility.VoyageByUserId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Storage for the voyage favorited by a user
 */
//@IdClass(VoyageByUserId.class)
@Entity @Data @Table(name = "voyage_fav")
@NoArgsConstructor @AllArgsConstructor
public class VoyageFav {
    /**
     * Constructs a specified VoyageFav object
     * @param userId the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage
     */
    @Id private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "voyage_id", nullable = false)
    private String voyageId;

}
