package com.portnet.utility;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Primary key type for Config
 */

@Data
@NoArgsConstructor @AllArgsConstructor
public class ConfigId implements Serializable {
    /**
     * Constructs a specified Voyage primary key
     * @param timeInterval the time interval at which web service should be called
     * @param apiKey the token for access to external API
     */
    private double timeInterval;
    private String apiKey;

    /**
     * Custom equals method to account all elements
     * @param object that could be ConfigId type or otherwise
     * @return true:  both objects are the same
     *                or have the same timeInterval and apiKey
     *         false: object is null or not ConfigId type
     *                or both objects have different timeInterval and/or apiKey
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ConfigId)) return false;
        ConfigId voyageId = (ConfigId) object;
        return timeInterval == voyageId.timeInterval &&
                apiKey.equals(voyageId.apiKey);
    }

    /**
     * Custom get method which accounts all elements
     * @return int representing hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(timeInterval, apiKey);
    }
}
