package com.example.cult_of_tim.cultoftim.util;

import com.example.cult_of_tim.cultoftim.dto.BookPurchaseDto;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Configuration
public class AppConfig {

    @Bean
    CacheManager cacheManager() {
        return new TimCaching();
    }

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Bean
    public Job createJob() throws Exception {
        return new JobBuilder("hello", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(createStep())
                .end().build();
    }


    @Bean
    public ItemWriter<BookPurchaseDto> itemWriter() {
        FlatFileItemWriter<BookPurchaseDto> itemWriter = new FlatFileItemWriter<>();
        var aggregator = new DelimitedLineAggregator<BookPurchaseDto>();
        aggregator.setDelimiter(",");
        var fieldExtractor = new BeanWrapperFieldExtractor<BookPurchaseDto>();
        fieldExtractor.setNames(new String[]{"id", "username", "purchaseDate"});
        aggregator.setFieldExtractor(fieldExtractor);

        itemWriter.setLineAggregator(aggregator);
        itemWriter.setResource(new FileSystemResource("./purchase_records.txt"));
        itemWriter.setAppendAllowed(true);

        return itemWriter;
    }



    @Autowired
    PurchaseReader purchaseReader;

    @Bean
    public Step createStep() throws Exception {
        SimpleStepBuilder<BookPurchaseDto, BookPurchaseDto> stepBuilder = new StepBuilder("file processor", jobRepository)
                .chunk(1, transactionManager);
        stepBuilder.reader(purchaseReader);
        purchaseReader.open(new ExecutionContext());
        stepBuilder.writer(itemWriter());
        stepBuilder.processor(new ItemProcessor<BookPurchaseDto, BookPurchaseDto>() {
            @Override
            public BookPurchaseDto process(BookPurchaseDto item) throws Exception {
                return item;
            }
        });
        stepBuilder.listener(new ItemReadListener<BookPurchaseDto>() {
            @Override
            public void beforeRead() {
                ItemReadListener.super.beforeRead();
            }

            @Override
            public void afterRead(BookPurchaseDto item) {
                ItemReadListener.super.afterRead(item);
            }

            @Override
            public void onReadError(Exception ex) {
                ItemReadListener.super.onReadError(ex);
            }
        });
        return stepBuilder.build();
    }

    @Scheduled(fixedRate = 10000)
    public void launchJob() throws Exception {
        Date date = new Date();
        Date previousRun = new Date();
        date = Date.from(date.toInstant().minus(10, ChronoUnit.SECONDS));
        JobExecution jobExecution = jobLauncher.run(createJob(), new JobParametersBuilder().addDate("launchDate", date)
                .addDate("previousRun", previousRun)
                .toJobParameters());
    }
}
