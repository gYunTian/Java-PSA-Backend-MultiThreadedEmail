package com.vsta.utility.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;


/**
 * Service to validate domains for UserService.
 */

@Service
public class DomainService {

    @Autowired
    private DomainProperties domainProperties;

    /**
     * Helper method to check if given domain is an accepted domain indicated in reload.properties.
     * @param userEmail Email provided by the user at registration. assumes valid email ie with '@'.
     * @return  boolean indicating if email is of an accepted domain name.
     */
    public boolean domainAccepted(String userEmail) {
        String[] acceptedDomains = domainProperties.getAcceptedDomains();

        // if no accepted domains indicated, no point in further checks
        if (acceptedDomains == null) {
            return false;
        }

        // since passed checks, there are accepted domains indicated
        String userDomain = userEmail.split("@")[1];
        return Arrays.asList(acceptedDomains).contains(userDomain); // domain valid only if it is an exact match with an accepted domain
    }

}
