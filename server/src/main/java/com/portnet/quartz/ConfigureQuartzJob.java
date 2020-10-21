package com.portnet.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Template to setup Quartz job
 * This template is run based on a cron interval
 */

@Configuration
public class ConfigureQuartzJob {
    
    @Autowired
    private QuartzProperties prop;
    
    @Bean
    public JobDetail jobADetails() {
        return JobBuilder.newJob(QuartzJob.class)
        .withIdentity("sampleJobA")
        .storeDurably()
        .build();
    }
    
    @Bean
    public CronTrigger jobATrigger(JobDetail jobADetail) {
        return TriggerBuilder.newTrigger()
        .forJob(jobADetail)
        .withIdentity("Test trigger")   
        .withSchedule(CronScheduleBuilder.cronSchedule(prop.getInterval()))
        .build();
    }
}
