package com.example.cult_of_tim.cultoftim.util;

import com.example.cult_of_tim.cultoftim.entity.Purchase;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Configuration
public class AppConfig {

    @Bean
    CacheManager cacheManager() {
        return new TimCaching();
    }


    //@Autowired
    //JobBuilder jobBuilder;

    //@Autowired
    //StepBuilder stepBuilder;

    //@Bean
    //JobRepository

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Bean
    public Job createJob() {
        return new JobBuilder("hello", jobRepository)
                .flow(createStep())
                .end().build();
        //return jobBuilderFactory.get("MyJob")
        //        .incrementer(new RunIdIncrementer())
        //        .flow(createStep()).end().build();
    }


    @Autowired
    PurchaseReader purchaseReader;

    public ItemWriter<Purchase> itemWriter() {
        FlatFileItemWriter<Purchase> itemWriter = new FlatFileItemWriter<>();
        itemWriter.setResource(new FileSystemResource("purchase_records.txt"));
        var aggregator = new DelimitedLineAggregator<Purchase>();
        aggregator.setDelimiter(",");
        var fieldExtractor = new BeanWrapperFieldExtractor<Purchase>();
        fieldExtractor.setNames(new String[]{"purchaseDate"});
        aggregator.setFieldExtractor(fieldExtractor);

        itemWriter.setLineAggregator(aggregator);
        return itemWriter;
    }

    @Bean
    public Step createStep() {
        SimpleStepBuilder<Purchase, Purchase> stepBuilder = new StepBuilder("file processor", jobRepository)
                .chunk(1, transactionManager);
        stepBuilder.reader(purchaseReader);
        stepBuilder.writer(itemWriter());
        stepBuilder.processor(new ItemProcessor<Purchase, Purchase>() {
            @Override
            public Purchase process(Purchase item) throws Exception {
                return item;
            }
        });
        return stepBuilder.build();
        //.reader(myCustomReader)
        //.processor(myCustomProcessor)
        //.writer(myCustomWriter)
        //.build();
    }

    @Scheduled(fixedRate = 2000)
    public void launchJob() throws Exception {
        Date date = new Date();
        JobExecution jobExecution = jobLauncher.run(createJob(), new JobParametersBuilder().addDate("launchDate", date)
                .toJobParameters());
    }
}
