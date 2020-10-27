package com.portnet.entity.storage;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Class to represent the externally configured accepted email domain names
 * Inputs comes from application.yml in the following prefix: spring.domain
 */

@Component  @Data
@ConfigurationProperties(prefix = "domain")
public class Domain {
    private String[] acceptedDomains;
}
