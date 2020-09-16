package com.portnet.entity.storage;

import com.portnet.utility.ConfigId;
import lombok.*;
import javax.persistence.*;

/**
 * Storage for web service configuration
 */

@Entity @Data @Table(name="configuration")
@NoArgsConstructor @AllArgsConstructor
@IdClass(ConfigId.class)
public class Config {
    /**
     * Constructs a specified Config object
     * @param timeInterval the time interval at which web service should be called
     * @param apiKey the token for access to external API
     */
    @Id @Column(name = "time_interval")
    private double timeInterval;

    @Id @Column(name = "access_key")
    private String apiKey;
}
