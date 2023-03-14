package com.tomlui2010.spring.batchprocessing.batch.config;

import com.tomlui2010.spring.batchprocessing.batch.listener.SendEmailJobCompletionNotificationListener;
import com.tomlui2010.spring.batchprocessing.batch.listener.UserImportJobCompletionNotificationListener;
import com.tomlui2010.spring.batchprocessing.batch.mapper.PersonItemMapper;
import com.tomlui2010.spring.batchprocessing.batch.config.processor.PersonEntityItemProcessor;
import com.tomlui2010.spring.batchprocessing.batch.config.processor.PersonItemProcessor;
import com.tomlui2010.spring.batchprocessing.batch.config.tasklet.SendEmailTasklet;
import com.tomlui2010.spring.batchprocessing.batch.config.tasklet.UsersEnteredTasklet;
import com.tomlui2010.spring.batchprocessing.batch.config.writer.PersonItemWriter;
import com.tomlui2010.spring.batchprocessing.batch.model.Person;
import com.tomlui2010.spring.batchprocessing.batch.model.PersonDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Log4j2
@Configuration
public class BatchConfiguration {
    @Autowired
    UsersEnteredTasklet usersEnteredTasklet;

    @Autowired
    SendEmailTasklet sendEmailTasklet;

    @Autowired
    DataSource dataSource;

    /*JOB #1*/

    /*Reader*/
    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("SampleCSVFileItemReader")
                .resource(new ClassPathResource("sample.csv"))
                .delimited()
                .delimiter(",")
                .names("firstname", "lastname","email")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    /*Processor*/
    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    /*Writer*/
    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO Person (firstname,lastname,email,subscription_status,email_sent_status) " +
                        "VALUES (:firstname,:lastname,:email, :subscription_status, :email_sent_status)")
                .dataSource(dataSource)
                .build();
    }

    /*Step*/
    @Bean
    public Step mainstep(JobRepository jobRepository,
                         PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Person> writer) {
        return new StepBuilder("MainStep", jobRepository)
                .<Person, Person> chunk(1000, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    /*Tasklet*/
    @Bean
    public Step taskletStepToCheckUsersEnteredCount(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("taskletStepToCheckUsersEnteredCount", jobRepository)
                .tasklet(usersEnteredTasklet, transactionManager)
                .build();
    }

    /*Tasklet*/
    @Bean
    public Step taskletStepToSendEmail(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("taskletStepToSendEmail", jobRepository)
                .tasklet(sendEmailTasklet, transactionManager)
                .build();
    }

    /*Job definition*/
    @Bean(name = "ImportUserJob")
    public Job importUserJob(JobRepository jobRepository,
                             UserImportJobCompletionNotificationListener listener, Step mainstep, Step taskletStepToCheckUsersEnteredCount, Step taskletStepToSendEmail) {
        return new JobBuilder("ImportUserAndSendEmail", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(mainstep)
                .next(taskletStepToCheckUsersEnteredCount)
                .next(taskletStepToSendEmail)
                .build();
    }


    /*JOB # 2*/
    @Bean
    public ItemReader<PersonDTO> activeSubscriberReader(DataSource dataSource) {
        String sql = "SELECT * FROM testdb.Person WHERE subscription_status='Y' and email_sent_status='N'";
        return new JdbcCursorItemReaderBuilder<PersonDTO>()
                .name("activeOrderReader")
                .sql(sql)
                .dataSource(dataSource)
                .rowMapper(new PersonItemMapper())
                .build();
    }

    @Bean
    public ItemProcessor<PersonDTO, PersonDTO> personEntityItemProcessor() {
        return new PersonEntityItemProcessor();
    }
    @Bean
    public ItemWriter<PersonDTO> personItemWriter() {
        return new PersonItemWriter();
    }

    @Bean
    public Step sendEmailStep(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager) {
        return new StepBuilder("SendEmail", jobRepository)
                .<PersonDTO, PersonDTO> chunk(10, transactionManager)
                .reader(activeSubscriberReader(dataSource))
                .processor(personEntityItemProcessor())
                .writer(personItemWriter())
                .build();
    }

    @Bean(name = "SendEmailJob")
    public Job sendEmailJob(JobRepository jobRepository,
                            SendEmailJobCompletionNotificationListener listener, Step sendEmailStep) {
        return new JobBuilder("SendEmail", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(sendEmailStep)
                .build();
    }
}
