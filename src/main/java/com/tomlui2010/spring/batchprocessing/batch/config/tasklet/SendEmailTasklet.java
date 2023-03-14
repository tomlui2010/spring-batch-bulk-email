package com.tomlui2010.spring.batchprocessing.batch.config.tasklet;

import com.tomlui2010.spring.batchprocessing.batch.service.EmailServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SendEmailTasklet implements Tasklet {

    @Autowired
    EmailServiceImpl emailService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("Sending a single mail");
        emailService.sendSimpleMessage("adminA@companyb.com","Sample Subject Test","Lots of text");
        return RepeatStatus.FINISHED;
    }
}
