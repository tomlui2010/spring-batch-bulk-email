package com.tomlui2010.spring.batchprocessing.batch.config.writer;

import com.tomlui2010.spring.batchprocessing.batch.model.PersonDTO;
import com.tomlui2010.spring.batchprocessing.batch.repo.PersonSubscribedRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PersonItemWriter implements ItemWriter<PersonDTO> {

    @Autowired
    @Qualifier("personsubscribedrepo")
    private PersonSubscribedRepository personSubscribedRepository;

    @Override
    public void write(Chunk<? extends PersonDTO> chunk) throws Exception {
        log.debug("item writer: {}", chunk.getItems().get(0));
        personSubscribedRepository.saveAllAndFlush(chunk);
    }
}
