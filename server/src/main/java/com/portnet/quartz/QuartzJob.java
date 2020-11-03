package com.portnet.quartz;

import java.nio.file.Path;

import com.portnet.config.PropertiesReloader;
import com.portnet.service.quartz.JobService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Job class It delegates the task to jobService
 * 
 */

@Component
public class QuartzJob implements Job {

    @Autowired
    private PropertiesReloader propLoader;

    @Autowired
    private JobService jobService;

    @Autowired
    private QuartzProperties prop;

    // Main method to execute
    @Override
    public void execute(JobExecutionContext context) {       
        jobService.executeJob();
    }
}
