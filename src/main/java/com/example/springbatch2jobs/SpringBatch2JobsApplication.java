package com.example.springbatch2jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class SpringBatch2JobsApplication {

    private final JobLauncher jobLauncher;
    private final Job jobOne;
    private final Job jobTwo;

    public SpringBatch2JobsApplication(JobLauncher jobLauncher, Job jobOne, Job jobTwo) {
        this.jobLauncher = jobLauncher;
        this.jobOne = jobOne;
        this.jobTwo = jobTwo;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBatch2JobsApplication.class, args);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void performJobOne() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(jobOne, params);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void performJobTwo() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(jobTwo, params);
    }
}