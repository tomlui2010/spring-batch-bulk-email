package com.tomlui2010.spring.batchprocessing.batch.config.processor;

import com.tomlui2010.spring.batchprocessing.batch.model.PersonDTO;
import com.tomlui2010.spring.batchprocessing.batch.service.EmailServiceImpl;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;


import org.springframework.beans.factory.annotation.Autowired;

import jakarta.mail.SendFailedException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PersonEntityItemProcessor implements ItemProcessor<PersonDTO, PersonDTO> {

    @Autowired
    EmailServiceImpl emailService;

    @Override
    public PersonDTO process(PersonDTO person) throws Exception {
        log.debug("processing for :", person.getFirstname());
        try {
            emailService.sendSimpleMessage(person.getEmail(), "Your subscription is active.","You are now a subscriber. Enjoy your subscription!!");

        } catch (SendFailedException sendFailedException) {
            log.debug("error: {}", sendFailedException.getMessage());
        }
        return person;
    }
}