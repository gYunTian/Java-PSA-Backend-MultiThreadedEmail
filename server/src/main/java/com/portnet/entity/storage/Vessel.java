package com.portnet.entity.storage;

import lombok.*;
import javax.persistence.*;

/**
 * An abstraction of a real-life PSA vessel
 */

@Entity @Data @Table(name="vessel")
@NoArgsConstructor @AllArgsConstructor
public class Vessel {
    /**
     * Constructs a specified Vessel object
     * @param name vessel's short name
     */
    @Id private String name;
}
