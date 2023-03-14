package com.tomlui2010.spring.batchprocessing.batch.config.processor;

import com.tomlui2010.spring.batchprocessing.batch.model.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) throws Exception {
        log.info("Customer " +  person.getLastname() + " is being processed" );
        Person modifiedPerson = new Person();
        modifiedPerson.setFirstname(person.getFirstname().toLowerCase());
        modifiedPerson.setLastname(person.getLastname().toLowerCase());
        modifiedPerson.setEmail(person.getEmail().toUpperCase());

        /*Subscription status*/
        if(modifiedPerson.getFirstname().length()>10){
            modifiedPerson.setSubscription_status("Y");
        } else {
            modifiedPerson.setSubscription_status("N");
        }

        return modifiedPerson;
    }
}
