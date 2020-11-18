// references:
//https://www.baeldung.com/spring-quartz-schedule
//https://dzone.com/articles/adding-quartz-to-spring-boot
package com.vsta.quartz;

import com.vsta.configuration.AutoWiringSpringBeanJobFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * This class contains all the methods required for setting up and controlling
 * of Quartz job.
 */

@Configuration
public class QuartzSheduler {

    @Autowired
    private QuartzProperties prop;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Uses AutoWiringSpringBeanJobFactory class to inject quartz objects such
     * as scheduler context, job data map, and trigger data entries as properties
     * from applicationContext into the job bean while creating an instance.
     * @return SpringBeanJobFactory to be used to inject properties for job.
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
     * @param trigger Trigger object that will trigger the execution of the job
     * @param job     JobDetail object that contains the details properties of a Job
     *                instance.
     * @param factory SchedulerFactoryBean object.
     * @return Scheduler object.
     * @throws SchedulerException Exception if any is thrown.
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
     * @return SchedulerFactoryBean object.
     * @throws IOException Exception if any is thrown.
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
     * @return Properties for the quartz job such as API key and URL.
     * @throws IOException Exception if any is thrown.
     */
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * Getter method to retrieve job details for only Quartz Job.
     * Calls the JobBuilder method from Quartz Scheduler to create a JobBuilder,
     * containing the job details and the class name of the Job to be executed.
     * The JobBuilder is then built and produced JobDetail.
     * @return JobDetail contains the details properties of a Job instance.
     */
    @Bean
    public JobDetail jobADetails() {
        return JobBuilder.newJob(QuartzJob.class).withIdentity("myJob")// .withIdentity("QuartzJob")
                .storeDurably().withDescription("Invokes job service...").build();
    }

    /**
     * This method defines, builds and return the job trigger.
     * @param job contains the details properties of a Job instance.
     * @return Trigger object that will trigger the execution of the job.
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
     * Getter method to retrieve job trigger. This method allows for custom interval.
     * @param job contains the details properties of a Job instance.
     * @param interval String representing the job interval.
     * @return Trigger object that will trigger the execution of the job.
     */
    public CronTrigger getTrigger(JobDetail job, String interval) {
        return TriggerBuilder.newTrigger().forJob(job).withIdentity("myTrigger").startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(interval)).build();
    }

    /**
     * Getter method to retrieve job details for any job type.
     * Similar to jobADetails() method, it calls the JobBuilder method from Quartz
     * Scheduler to create a JobBuilder which is then built and produced JobDetail.
     * @param jobType type of job to be executed.
     * @return JobDetail contains the details properties of a Job instance.
     */
    public JobDetail getJobDetail(Class<? extends Job> jobType) {
        return JobBuilder.newJob(jobType)
                .withIdentity("myJob")
                .storeDurably()
                .withDescription("Invokes job service...")
                .build();
    }

}
