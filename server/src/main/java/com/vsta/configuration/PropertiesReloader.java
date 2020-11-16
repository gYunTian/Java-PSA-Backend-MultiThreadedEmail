// https://stackoverflow.com/questions/40287771/how-to-reload-a-value-property-from-application-properties-in-spring/40288822#40288822
package com.vsta.configuration;

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
 * Class containing method that hot reload environment properties.
 */

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

    /**
     * This method is scheduled to auto run every 10 secs. It will refresh the file
     * loaded in memory so properties within it will be reread into the app.
     * @throws IOException
     */
    @Scheduled(fixedRate = 10000)
    public void reload() throws IOException {

        try {
            prevInterval = prop.getInterval();
            printer("Reloading properties");

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
                printer("Updating cron job interval");
                printer("Interval changed from " + prevInterval + " to " + newInterval);
                updateCronInterval();
                printer("Updated cron interval");
            }
            printer("Reloaded properties");

        } catch (IOException e) {
            printer("Your reload.properties file cannot be located - " + e);
            printer("Make sure it is in resources folder");
        }
    }

    /**
     * This method will update the cron job's interval. It clears the existing
     * scheduled jobs and create a new job based on the updated interval.
     */
    public void updateCronInterval() {
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
    }

    /**
     * Custom console message printer for status and debugging.
     */
    public void printer(String msg) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = now.format(formatter);

        System.out.println(date + "  - Prop reloader: " + msg);
    }
}
