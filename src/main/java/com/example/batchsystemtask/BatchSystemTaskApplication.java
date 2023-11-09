package com.example.batchsystemtask;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class BatchSystemTaskApplication {


  @Bean
  public Job demoJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
    SystemCommandTasklet commandTasklet = new SystemCommandTasklet();
    commandTasklet.setTimeout(60000L);
    commandTasklet.setCommand("/usr/bin/false");
    commandTasklet.afterPropertiesSet();
    return new JobBuilder("demo-job", jobRepository)
        .start(new StepBuilder("demo-step", jobRepository)
            .tasklet(commandTasklet, transactionManager)
            .build())
        .build();
  }

  public static void main(String[] args) {
    SpringApplication.run(BatchSystemTaskApplication.class, args);
  }

}
