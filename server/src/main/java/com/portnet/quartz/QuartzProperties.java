package com.portnet.quartz;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** 
 * Class to represent the external configuration properties for scheduling jobs
 * Input comes from application.yml in the following prefix: spring.quartz.properties
 */

@Component
@ConfigurationProperties(prefix = "spring.quartz.properties")
public class QuartzProperties {
    private String interval;
    private boolean enabled;
    private String apiKey;
    private String apiURL;
    
    // externalized plusdays
    private int plusDays;

    // Json date format not externalized as API implementation is expected to not change
    // dateFrom is always set to today 
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT")
    private LocalDate dateFrom = LocalDate.now();
    
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT")
    private LocalDate dateTo;
    
        
    // Getter & Setter functions
    public String getInterval() {
        return interval;
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    public String getApiKey() {
        return apiKey;
    }
    
    public String getApiURL() {
        return apiURL;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public int getPlusDays() {
        return plusDays;
    }

    public void setPlusDays(int plusDays) {
        this.plusDays = plusDays;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo = dateFrom.plusDays(plusDays);
    }

}
