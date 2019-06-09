package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anson
 * @date 2019/6/9
 */

@Configuration
public class ClientInfoJobConfig {

    @Bean(name = "updateClientInfoJob")
    public Job updateClientInfo(JobBuilderFactory jobBuilderFactory,
                                @Qualifier("updateClientInfoStep")Step updateClientInfoStep) {
        return jobBuilderFactory.get("updateClientInfoJob")
                .incrementer(new RunIdIncrementer())
                .flow(updateClientInfoStep)
                .end()
                .build();
    }
}
