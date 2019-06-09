package com.example.demo.config;

import com.example.demo.domain.Users;
import com.example.demo.service.ClientInfoService;
import com.example.demo.service.RowToDataClientInfoProcessor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Anson
 * @date 2019/6/9
 */

@Configuration
public class ClientInfoStepConfig {
    int ID =  0;
    int NAME = 1;
    Integer[] included_fields = {
            ID,
            NAME
    };

    String [] fields = {
            "id",
            "name"
    };

    @Bean(name = "clientInfoExecutor")
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor("clientInfoExecutor");
        asyncTaskExecutor.setConcurrencyLimit(5);
        return asyncTaskExecutor;
    }

    @Bean(name = "updateClientInfoStep")
    public Step updateClientInfoStep(StepBuilderFactory stepBuilderFactory,
                                     final ClientInfoService clientInfoService,
                                     @Value("spring.batch.size") int batchSize) {
        return stepBuilderFactory.get("updateClientInfoStep")
                .<String[], Users>chunk(batchSize)
                .reader(updateClientInfoReader(null))
                .processor(new RowToDataClientInfoProcessor())
                .writer(updateClientInfoWriter(clientInfoService))
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name = "updateClientInfoReader")
    public FlatFileItemReader<String []> updateClientInfoReader(@Value("#{jobParameters['filename']}") final String filename) {
        return new FlatFileItemReaderBuilder<String[]>()
                .name("updateClientInfoReader")
                .resource(new ClassPathResource(filename))
                .delimited().includedFields(included_fields)
                .names(fields)
                .fieldSetMapper((fieldSet -> fieldSet.getValues()))
                .linesToSkip(1)
                .distanceLimit(0)
                .build();
    }

    public ItemWriter<Users> updateClientInfoWriter(ClientInfoService clientInfoService) {
        return userList -> {
            userList.stream().filter(Objects::nonNull).collect(Collectors.toList()).forEach(u ->clientInfoService.updateUser(u));
        }
    }
}

