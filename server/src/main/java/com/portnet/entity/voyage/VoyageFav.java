package com.portnet.entity.voyage;

import com.portnet.utility.VoyageFavId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Storage for the voyage favorited by a user
 */

@Entity @Data @Table(name="favourites")
@NoArgsConstructor @AllArgsConstructor
@IdClass(VoyageFavId.class)
public class VoyageFav implements Serializable {
    /**
     * Constructs a specified VoyageFav object
     * @param userId the auto-generated ID of the user
     * @param voyageId the auto-generated ID of the voyage, identified by vesselName and voyageNum
     * */
    @Id private int userId;
    @Id private int voyageId;
}
