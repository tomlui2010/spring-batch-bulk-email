package com.tomlui2010.spring.batchprocessing.batch.config.tasklet;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class UsersEnteredTasklet implements Tasklet {


    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UsersEnteredTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        log.info("Checking for number of users added");
        int users = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Person;", Integer.class);
        log.info("Number of users added: " + users);
        return RepeatStatus.FINISHED;
    }
}
