package com.vsta.quartz;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

/**
 * Represents the external configuration properties required for scheduling of
 * quartz jobs The Input are from reload.properties file and uses the following
 * PREFIX: spring.quartz.properties Consists of getter methods to retrieve the
 * properties
 */

@Component
// @ConfigurationProperties(prefix = "spring.quartz.properties")
public class QuartzProperties {

    @Autowired
    private StandardEnvironment environment;

    /**
     * Constant variable that contains the file name.
     */
    private static final String PREFIX = "quartz.properties.";

    /**
     * Date variable with encoded JSON pattern and timezone. This variable
     * represents the from part in a date range.
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate dateFrom = LocalDate.now();

    /**
     * DateTo variable with encoded JSON pattern and timezone. ThIs variable
     * represents the to part in a date range.
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate dateTo;

    /**
     * Gets the interval value in the properties file
     * 
     * @return String - Quartz job Cron interval
     */
    public String getInterval() {
        return environment.getProperty(PREFIX + "interval");
    }

    /**
     * Gets the enabled value in the properties file
     * 
     * @return Boolean - Quartz job enabled value
     */
    public Boolean isEnabled() {
        try {
            return Boolean.parseBoolean(environment.getProperty(PREFIX + "jobEnabled"));
        } catch (Exception e) {
            System.out
                    .println("An invalid boolean value is used for isEnabled property, default of true will be used.");
            return true;
        }
    }

    /**
     * Gets the api key value in the properties file
     * 
     * @return String - target api key
     */
    public String getApiKey() {
        return environment.getProperty(PREFIX + "apiKey");
    }

    /**
     * Gets the api url value in the properties file
     * 
     * @return String - target api url
     */
    public String getApiURL() {
        return environment.getProperty(PREFIX + "apiUrl");
    }

    /**
     * Gets the plus days value in the properties file. This variable represents the
     * days 'gap' inbetween a date range.
     * 
     * @return int - plus days
     */
    public int getPlusDays() {
        try {
            return Integer.parseInt(environment.getProperty(PREFIX + "plusDays").trim());
        } catch (NumberFormatException e) {
            System.out.println(
                    "PlusDays property in reload.properties is not a valid int, a default value of 6 will be used.");
            return 6;
        }
    }

    /**
     * Gets the private dateFrom instance variable.
     * 
     * @return LocalDate - dateFrom
     */
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    /**
     * Gets the private daateTo instance variable.
     * 
     * @return LocalDate - dateTo
     */
    public LocalDate getDateTo() {
        return dateTo = dateFrom.plusDays(this.getPlusDays());
    }

    /**
     * Gets the reload quartz job interval value in the properties file..
     * 
     * @return boolean - isReloadInterval
     */
    public boolean isReloadInterval() {
        try {
            return Boolean.parseBoolean(environment.getProperty(PREFIX + "reloadInterval"));
        } catch (Exception e) {
            System.out.println(
                    "An invalid boolean value is used for reloadInterval property, default of false will be used.");
            return false;
        }
    }

}
