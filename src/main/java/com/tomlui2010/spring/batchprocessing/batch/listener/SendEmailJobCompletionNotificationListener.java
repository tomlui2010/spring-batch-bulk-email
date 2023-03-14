package com.tomlui2010.spring.batchprocessing.batch.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;


/*JobExecutionListenerSupport is a convenience class provided by Spring Batch that simplifies
the implementation of JobExecutionListener. It provides default empty implementations of all methods in the interface,
so that you can simply override the ones you need.*/
@Log4j2
@Component
public class SendEmailJobCompletionNotificationListener extends JobExecutionListenerSupport {
    public void beforeJob(JobExecution jobExecution) {
        log.info(jobExecution.getJobInstance().getInstanceId() + " has started and is currently in " + jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info(jobExecution.getJobInstance().getInstanceId() + " JOB Completed! Emails are sent to subscribers.");
        }
    }
}
