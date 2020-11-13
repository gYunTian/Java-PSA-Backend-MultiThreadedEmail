// https://stackoverflow.com/questions/40287771/how-to-reload-a-value-property-from-application-properties-in-spring/40288822#40288822
package com.vsta.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import com.vsta.quartz.QuartzJob;
import com.vsta.quartz.QuartzProperties;
import com.vsta.quartz.QuartzSheduler;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import lombok.Cleanup;

/**
 * Class to hot reload properties
 */

// to-do
// on reload, intercept quartz schedule and create new trigger
// on cron trigger, run properties reloader as well
// check file modified before reading else same

@Component
public class PropertiesReloader {

    @Autowired
    private StandardEnvironment environment;

    @Autowired
    private QuartzProperties prop;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private QuartzSheduler quartzSheduler;

    private static final String FILE_NAME = "reload.properties";
    private Path configPath;
    private String prevInterval;
    private String newInterval;

    // private Properties old;

    @Scheduled(fixedRate = 10000)
    public void reload() throws IOException {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = now.format(formatter);

        try {
            prevInterval = prop.getInterval();
            System.out.println(date + "  - Prop reloader: Reloading properties");
            MutablePropertySources propertySources = environment.getPropertySources();
            PropertySource<?> resourcePropertySource = propertySources.get("class path resource [reload.properties]");
            Properties properties = new Properties();

            configPath = Paths.get("src/main/resources/" + FILE_NAME).toAbsolutePath();
            @Cleanup
            InputStream inputStream = Files.newInputStream(configPath);
            properties.load(inputStream);
            newInterval = properties.getProperty("quartz.properties.interval");

            propertySources.replace("class path resource [reload.properties]",
                    new PropertiesPropertySource("class path resource [reload.properties]", properties));

            if (!(prevInterval.equals(newInterval)) && prop.isReloadInterval()) {
                System.out.println(date + "  - Prop reloader: Updating interval");
                System.out.println(date + "  - Prop reloader: Interval changed from " + prevInterval + " to " + newInterval);

                try {
                    Scheduler scheduler = schedulerFactoryBean.getScheduler();
                    scheduler.clear();
                    scheduler.standby();

                    // generating new job and trigger
                    JobDetail jobDetail = quartzSheduler.getJobDetail(QuartzJob.class);
                    CronTrigger jobTrigger = quartzSheduler.getTrigger(jobDetail, prop.getInterval());

                    scheduler.start();
                    scheduler.scheduleJob(jobDetail, jobTrigger);

                } catch (SchedulerException e) {
                    e.printStackTrace();
                }

                System.out.println(date + "  - Prop reloader: Updated interval");
            }

            System.out.println(date + "  - Prop reloader: Reloaded properties");

        } catch (IOException e) {
            System.out.println(date + "  - Prop reloader: Your reload.properties file cannot be located - " + e);
            System.out.println(date + "  - Prop reloader: Make sure it is in resources folder");
            System.out.println(date + "  - Prop reloader: Quartz job will be stopped");
        }
    }
}
