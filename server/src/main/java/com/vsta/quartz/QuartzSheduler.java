// references:
//https://www.baeldung.com/spring-quartz-schedule
//https://dzone.com/articles/adding-quartz-to-spring-boot
package com.vsta.quartz;

import java.io.IOException;
import java.util.Properties;

import com.vsta.configuration.AutoWiringSpringBeanJobFactory;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * This class contains all the methods required for setting up and controlling
 * of Quartz job
 */

@Configuration
public class QuartzSheduler {

    @Autowired
    private QuartzProperties prop;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * The SpringBeanJobFactory provides support for injecting the scheduler
     * context, job data map, and trigger data entries as properties into the job
     * bean while creating an instance. Taken from baeldung.
     */
    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    /**
     * The Scheduler is used to register quartz jobs and trigger. Once registered,
     * the jobs can be executed based on the trigger.
     */
    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory) throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        return scheduler;
    }

    /**
     * Spring's SchedulerFactoryBean provides bean-style usage for configuring a
     * Scheduler, manages its life-cycle within the application context, and exposes
     * the Scheduler as a bean for dependency injection. Taken from baeldung.
     * 
     * @return
     * @throws IOException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory());
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    /**
     * This method loads variables inside the quartz.properties file.
     */
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * This method defines, builds and return the job detail.
     */
    @Bean
    public JobDetail jobADetails() {
        return JobBuilder.newJob(QuartzJob.class).withIdentity("myJob")// .withIdentity("QuartzJob")
                .storeDurably().withDescription("Invokes job service...").build();
    }

    /**
     * This method defines, builds and return the job trigger.
     * @param job
     * @return
     */
    @Bean
    public CronTrigger jobATrigger(JobDetail job) {
        try {
            return TriggerBuilder.newTrigger().forJob(job).withIdentity("myTrigger")// .withIdentity("JobTrigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(prop.getInterval())).build();
        } catch (Exception e) {
            System.out.println("Invaild cron trigger, a default of hourly will be used");
            return TriggerBuilder.newTrigger().forJob(job).withIdentity("myTrigger")// .withIdentity("JobTrigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/1 ? * * *")).build();
        }
    }

    /**
     * Getter method to retrieve job trigger. This method allows for custom
     * interval.
     * @param interval
     * @param JobDetail
     * @return trigger
     */
    public CronTrigger getTrigger(JobDetail job, String interval) {
        return TriggerBuilder.newTrigger().forJob(job).withIdentity("myTrigger").startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(interval)).build();
    }

    /**
     * Getter method to retrieve job details.
     * @param jobType
     * @param JobDetail
     * @return job detail
     */
    public JobDetail getJobDetail(Class<? extends Job> jobType) {
        return JobBuilder.newJob(jobType).withIdentity("myJob").storeDurably().withDescription("Invokes job service...")
                .build();
    }

}
