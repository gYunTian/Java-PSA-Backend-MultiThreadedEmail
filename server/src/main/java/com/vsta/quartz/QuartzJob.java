package com.vsta.quartz;

import com.vsta.service.quartz.JobService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Quartz Job clas.
 * Uses jobService
 * 
 */

@Component
public class QuartzJob implements Job {

    @Autowired
    private JobService jobService;
    
    // Main method to execute
    @Override
    public void execute(JobExecutionContext context) {
        jobService.executeJob();
    }
}
