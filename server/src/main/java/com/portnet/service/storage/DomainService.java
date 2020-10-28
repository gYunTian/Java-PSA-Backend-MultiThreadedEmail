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
     * Helper method to check if given domain is (part of or otherwise) an accepted domain indicated in reload.properties
     * @param userEmail the email provided by the user at registration. assumes valid email ie with '@'
     * @return boolean with 1 indicating that email is of an accepted domain name
     */
    public boolean domainAccepted(String userEmail) {
        // get accepted domains
        String[] acceptedDomains = domain.getAcceptedDomains();

        // if none accepted, no point in further checks
        if (acceptedDomains == null) return false;

        // there are accepted domains, check if user's domain is 1 of them
        String userDomain = userEmail.split("@")[1];
        return Arrays.stream(acceptedDomains).parallel().anyMatch(userDomain::contains);
    }

}
