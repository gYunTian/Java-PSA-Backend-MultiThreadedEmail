package com.portnet.entity.voyage;

import com.portnet.utility.VoyagebyUserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Storage for the voyage subscribed by a user
 */

@Entity @Data @Table
@NoArgsConstructor @AllArgsConstructor
@IdClass(VoyagebyUserId.class)
public class VoyageSub implements Serializable {
    /**
     * Constructs a specified VoyageSub object
     * @param userId the auto-generated ID of the user
     * @param voyageId the auto-generated ID of the voyage, identified by vesselName and voyageNum
     */
    @Id private int userId;
    @Id private int voyageId;
}
