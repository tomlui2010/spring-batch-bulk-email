package com.tomlui2010.spring.batchprocessing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BatchProcessingApplication {
    public static void main(String[] args) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        /*Starting point of the applicationSpringApplication.run() starts the Spring Boot application context
        and runs all the beans and components
        defined in the application, including the Spring Batch jobs and steps. SpringApplication.exit() gracefully
        shuts down the application context and returns an exit code that represents the status of the application after
        it has completed. Finally, System.exit() terminates the JVM with the exit code
        returned by SpringApplication.exit().
        Please ensure that te following flag is set to true to make sure the job is run automatically
        spring.batch.job.enabled=true*/
        //System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, args)));

		/*Alternate approach when multiple jobs are defined and needs to be run sequentially*/
        ConfigurableApplicationContext ctx = SpringApplication.run(BatchProcessingApplication.class, args);
        JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
        Job importUserJob = (Job) ctx.getBean("ImportUserJob");
        Job sendEmailJob = (Job) ctx.getBean("SendEmailJob");
        jobLauncher.run(importUserJob, new JobParameters());
        jobLauncher.run(sendEmailJob, new JobParameters());
    }
}
