package com.vsta.quartz;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

/**
 * Class to represent the external configuration properties required for scheduling of quartz jobs 
 * The Input are from application.properties file and uses the following PREFIX: spring.quartz.properties Consists of getter
 * Consists of getter methods to retrieve the properties
 * 
 * This is placed here instead of entity folder to represent the domain usage
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
        return Boolean.parseBoolean(environment.getProperty(PREFIX + "jobEnabled"));
    }

    public String getApiKey() {
        return environment.getProperty(PREFIX + "apiKey");
    }

    public String getApiURL() {
        return environment.getProperty(PREFIX + "apiUrl");
    }

    public int getPlusDays() {
        return Integer.parseInt(environment.getProperty(PREFIX + "plusDays"));
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo = dateFrom.plusDays(this.getPlusDays());
    }

    public boolean isReloadInterval() {
        return Boolean.parseBoolean(environment.getProperty(PREFIX + "reloadInterval"));
    }

}
