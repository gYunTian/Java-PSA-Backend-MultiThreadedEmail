package com.portnet.service.storage;

import com.portnet.entity.storage.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;


/**
 * Helper service tasks
 * - validation for UserService
 */

@Service
public class DomainService {

    @Autowired
    private Domain domain;

    /**
     * Helper method to check if given domain is an accepted domain indicated in reload.properties
     * @param userEmail the email provided by the user at registration. assumes valid email ie with '@'
     * @return boolean with 1 indicating that email is of an accepted domain name
     */
    public boolean domainAccepted(String userEmail) {
        String[] acceptedDomains = domain.getAcceptedDomains();

        // if none accepted, no point in further checks
        if (acceptedDomains == null) return false;

        // there are accepted domains indicated
        String userDomain = userEmail.split("@")[1];

        return Arrays.asList(acceptedDomains).contains(userDomain); // domain valid only if it is an exact match with an accepted domain
        // return Arrays.stream(acceptedDomains).parallel().anyMatch(userDomain::contains);  // domain valid as long as a part is contained
    }

}
