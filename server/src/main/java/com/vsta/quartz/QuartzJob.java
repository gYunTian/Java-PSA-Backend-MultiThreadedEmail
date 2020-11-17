package com.vsta.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Quartz Job class which contains the main method
 * that will be auto run by quartz job. Once a job's
 * trigger fires, it will invoke the jobService's
 * executeJob method.
 */

@Component
public class QuartzJob implements Job {

    @Autowired
    private JobService jobService;

    /**
     * Main method to be used by quartz job.
     * @param context Provides the job instance with
     * information regarding its runtime environment,
     * which includes handles to the scheduler and
     * trigger, and the job's JobDetail object.<br>
     * Taken from baeldung:
     * https://www.baeldung.com/spring-quartz-schedule
     */
    @Override
    public void execute(JobExecutionContext context) {
        jobService.executeJob();
    }
}
