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
     * Helper method to check if email is of accepted domain name
     * @param userEmail the email provided by the user at registration
     * @return boolean with 1 indicating email exists
     */
    public boolean domainAccepted(String userEmail) {
        // get accepted domains
        String[] acceptedDomains = domain.getAcceptedDomains();
        // if none accepted, no point in further checks
        if (acceptedDomains == null) return false;

        // there are accepted domains, check if user's domain is 1 of them
        String userDomain = userEmail.split("@")[1];
        return Arrays.asList(acceptedDomains).contains(userDomain);
    }

}
