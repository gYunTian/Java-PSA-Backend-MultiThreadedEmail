package com.vsta.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * Utility class to perform validation checks,
 * returning an error response if valid, else null.
 */

@Service
public class ValidationUtil {

    private static final String emptyMsgTemplate = "%s must be provided";

    /**
     * Check if specified details passes validity checks.
     * @param attribute Attribute to be checked.
     * @param attributeName String of attribute to be checked.
     * @param errorMsgPrefix String to be appended in front of error message.
     * @return  ResponseEntity with an error message and 400 status code if invalid,
     *          else null.
     */
    public static ResponseEntity<String> emptyStringResponse(String attribute, String attributeName, String errorMsgPrefix) {

        if (attribute == null || attribute.length() == 0 || attribute.charAt(0) == ' ') {
            return new ResponseEntity<>(
                    String.format(errorMsgPrefix + emptyMsgTemplate, attributeName),
                    HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    /**
     * Check if specified details passes validity checks.
     * @param fields Map containing attributes to be checked.
     * @param errorMsgPrefix String to be appended in front of error message.
     * @return  ResponseEntity with an error message and 400 status code if invalid,
     *          else null.
     */
    public static ResponseEntity<String> emptyFieldsResponse(Map<String,String> fields, String errorMsgPrefix) {

        Set<String> attributeNames = fields.keySet();
        for (String name : attributeNames) {
            String value = fields.get(name);

            ResponseEntity<String> emptyStringResponse = emptyStringResponse(value, name, errorMsgPrefix);
            if (emptyStringResponse != null) {
                return emptyStringResponse;
            }
        }

        return null;
    }

}
