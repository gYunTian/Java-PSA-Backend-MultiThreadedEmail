package com.vsta.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Quartz Job clas. Contains the main method that will be auto run by quartz
 * job. It uses jobService's executeJob method.
 * 
 */

@Component
public class QuartzJob implements Job {

    @Autowired
    private JobService jobService;

    /**
     * Main method to be used by quartz job.
     */
    @Override
    public void execute(JobExecutionContext context) {
        jobService.executeJob();
    }
}
