package com.portnet.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

// Class definition to setup Quartz job
// This class uses the QuartzJob and QuartzProperties class to setup the jobs
// application.yml -> QuartzProperties + QuartzJob -> ConfigureQuartzJob -> Executed
@Configuration
public class ConfigureQuartzJob {
    
    // Autowired is similar to importing class but in this is a auto dependecy injection
    @Autowired
    private QuartzProperties prop;

    @Bean
    public JobDetail jobADetails() {
        return JobBuilder.newJob(QuartzJob.class)
        .withIdentity("sampleJobA")
        .storeDurably()
        .build();
    }
    
    // Returns a trigger object that contains detail about the job and schedule
    @Bean
    public CronTrigger jobATrigger(JobDetail jobADetail) {
        return TriggerBuilder.newTrigger()
        .forJob(jobADetail)
        .withIdentity("Test trigger")
        .withSchedule(CronScheduleBuilder.cronSchedule(prop.getInterval()))
        .build();
    }
}
