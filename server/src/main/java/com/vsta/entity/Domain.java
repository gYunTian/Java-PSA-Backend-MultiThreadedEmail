package com.vsta.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;


/**
 * Class to represent the externally configured accepted email domain names
 * Inputs comes from application.properties in the following prefix: domain
 */

@Component
@Data
//@ConfigurationProperties(prefix = "domain")
public class Domain {

    private String[] acceptedDomains;

    @Autowired
    private StandardEnvironment environment;

    private static final String PREFIX = "domain.";

    public String[] getAcceptedDomains(){
        return environment.getProperty(PREFIX+"acceptedDomains", String[].class);
    }

}
