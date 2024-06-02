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

@Configuration
public class JobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public JobConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job jobOne(Step stepOne) {
        return new JobBuilder("jobOne", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(stepOne)
                .end()
                .build();
    }

    @Bean
    public Job jobTwo(Step stepOne) {
        return new JobBuilder("jobTwo", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(stepOne)
                .end()
                .build();
    }

    @Bean
    public Step stepOne() {
        return new StepBuilder("stepOne", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Result of 1+1 for jobOne: " + (1+1));
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step stepTwo() {
        return new StepBuilder("stepTwo", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Result of 2+2 for jobTwo: " + (2+2));
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}