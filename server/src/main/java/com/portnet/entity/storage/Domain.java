package com.portnet.entity.storage;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Class to represent the externally configured accepted email domain names
 * Inputs comes from application.properties in the following prefix: domain
 */

@Component
@Data
//@ConfigurationProperties(prefix = "domain")
public class Domain {

    @Autowired
    private StandardEnvironment environment;

    private static final String PREFIX = "domain.";

    String[] acceptedDomains = environment.getProperty(PREFIX+"acceptedDomains[0]", String[].class);

}
