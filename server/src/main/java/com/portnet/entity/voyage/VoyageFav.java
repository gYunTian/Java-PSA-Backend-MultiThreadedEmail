package com.portnet.entity.voyage;

import com.portnet.utility.VoyageId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * An abstraction of a real-life PSA outgoing voyage
 */

@Entity @Data @Table(name="favourites")
@NoArgsConstructor @AllArgsConstructor
public class VoyageFav implements Serializable {
    /**
     * Constructs a specified VoyageFav object
     * @param userId the auto-generated ID of the user
     * @param voyageId which identifies a unique voyage (vesselName and voyageNum)
     * */

    @Id @Column(name = "user_id")
    private String userId;

    @EmbeddedId
    private VoyageId voyageId;
}
