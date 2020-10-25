package com.portnet.entity.voyage;

import com.portnet.utility.VoyageByUserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Storage for the voyage subscribed by a user
 */
//@IdClass(VoyageByUserId.class)
@Entity @Data @Table(name = "voyage_sub")
@NoArgsConstructor @AllArgsConstructor
public class VoyageSub implements Serializable {
    /**
     * Constructs a specified VoyageSub object
     * @param userId the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage
     */
    @Id private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "voyage_id", nullable = false)
    private String voyageId;
}
