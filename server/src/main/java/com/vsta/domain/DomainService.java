package com.vsta.domain;

import com.vsta.domain.DomainProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;


/**
 * Helper service tasks to validate domains for UserService
 */
/**
 * Represents the externally configured accepted email domain names.
 * Inputs comes from reload.properties in the following prefix: domain.
 * Consists of getter method to retrieve the accepted domains.
 */


@Service
public class DomainService {

    @Autowired
    private DomainProperties domainProperties;

    /**
     * Helper method to check if given domain is an accepted domain indicated in reload.properties
     * @param userEmail Email provided by the user at registration. assumes valid email ie with '@'
     * @return boolean with 1 indicating that email is of an accepted domain name
     */
    public boolean domainAccepted(String userEmail) {
        String[] acceptedDomains = domainProperties.getAcceptedDomains();

        // if none accepted, no point in further checks
        if (acceptedDomains == null) return false;

        // there are accepted domains indicated
        String userDomain = userEmail.split("@")[1];

        return Arrays.asList(acceptedDomains).contains(userDomain); // domain valid only if it is an exact match with an accepted domain
        // return Arrays.stream(acceptedDomains).parallel().anyMatch(userDomain::contains);  // domain valid as long as a part is contained
    }

}
