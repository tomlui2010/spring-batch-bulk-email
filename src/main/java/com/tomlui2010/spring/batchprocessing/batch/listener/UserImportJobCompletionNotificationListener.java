package com.tomlui2010.spring.batchprocessing.batch.listener;


import lombok.extern.log4j.Log4j2;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/*JobExecutionListenerSupport is a convenience class provided by Spring Batch that simplifies
the implementation of JobExecutionListener. It provides default empty implementations of all methods in the interface,
so that you can simply override the ones you need.*/

@Log4j2
@Component
public class UserImportJobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserImportJobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void beforeJob(JobExecution jobExecution) {
        log.info(jobExecution.getJobInstance().getInstanceId() + " has started and is currently in " + jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info(jobExecution.getJobInstance().getInstanceId() + " JOB Completed! Check the Number of users inserted.");
            int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Person", Integer.class);
            if (count == 10005) {
                log.info("Job is success All users added.");
            } else {
                log.info("Something went wrong, wrong total count of users found: " + count);
            }
        }
    }
}
