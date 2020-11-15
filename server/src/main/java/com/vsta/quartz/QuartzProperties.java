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

    private static final String PREFIX = "quartz.properties.";

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate dateFrom = LocalDate.now();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate dateTo;

    // Getter functions
    public String getInterval() {
        return environment.getProperty(PREFIX + "interval");
    }

    public Boolean isEnabled() {
        try {
            return Boolean.parseBoolean(environment.getProperty(PREFIX + "jobEnabled"));
        } catch (Exception e) {
            System.out
                    .println("An invalid boolean value is used for isEnabled property, default of true will be used.");
            return true;
        }
    }

    public String getApiKey() {
        return environment.getProperty(PREFIX + "apiKey");
    }

    public String getApiURL() {
        return environment.getProperty(PREFIX + "apiUrl");
    }

    public int getPlusDays() {
        try {
            return Integer.parseInt(environment.getProperty(PREFIX + "plusDays").trim());
        } catch (NumberFormatException e) {
            System.out.println(
                    "PlusDays property in reload.properties is not a valid int, a default value of 6 will be used.");
            return 6;
        }
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo = dateFrom.plusDays(this.getPlusDays());
    }

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
