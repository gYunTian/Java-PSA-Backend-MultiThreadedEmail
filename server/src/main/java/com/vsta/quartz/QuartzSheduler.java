//https://www.baeldung.com/spring-quartz-schedule
//https://dzone.com/articles/adding-quartz-to-spring-boot
package com.vsta.quartz;

import java.io.IOException;
import java.util.Properties;

import com.vsta.config.AutoWiringSpringBeanJobFactory;

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
 * Quartz scheduler to control and setup Quartz job
 * 
 */

@Configuration
public class QuartzSheduler {

    @Autowired
    private QuartzProperties prop;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory) throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        return scheduler;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory());
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public JobDetail jobADetails() {
        return JobBuilder.newJob(QuartzJob.class).withIdentity("myJob")// .withIdentity("QuartzJob")
                .storeDurably().withDescription("Invokes job service...").build();
    }

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

    public CronTrigger getTrigger(JobDetail job, String interval) {
        return TriggerBuilder.newTrigger().forJob(job).withIdentity("myTrigger").startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(interval)).build();
    }

    public JobDetail getJobDetail(Class<? extends Job> jobType) {
        return JobBuilder.newJob(jobType).withIdentity("myJob").storeDurably().withDescription("Invokes job service...")
                .build();
    }

}
