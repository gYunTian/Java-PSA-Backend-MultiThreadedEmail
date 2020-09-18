package com.portnet.entity.storage;

import lombok.*;
import javax.persistence.*;

/**
 * Storage for accepted email domains
 */

@Entity @Data @Table(name="domain")
@NoArgsConstructor @AllArgsConstructor
public class Domain {
    /**
     * Constructs a specified User object
     * @param name the accepted domain name
     */
    @Id private String name;
}
