package com.portnet.entity.storage;

import com.portnet.utility.ConfigId;
import lombok.*;
import javax.persistence.*;

/**
 * Storage for web service configuration
 */

@Entity @Data @Table
@NoArgsConstructor @AllArgsConstructor
@IdClass(ConfigId.class)
public class Config {
    /**
     * Constructs a specified Config object
     * @param apiKey the token for access to external API
     * @param timeInterval the time interval at which web service should be called
     */
    @Id private double timeInterval;
    @Id private String apiKey;
}
