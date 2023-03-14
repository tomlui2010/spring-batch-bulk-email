package com.tomlui2010.spring.batchprocessing.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.SendFailedException;


/*this class provides a simple way to send emails using Spring Framework's JavaMailSender interface,
and can be used as a dependency in other Spring components to send emails as part of a larger application.*/
@Component
public class EmailServiceImpl {

    private static final String FROM_ADDRESS = "noreply@batchsend.com";
    @Autowired
    private JavaMailSender emailDispatcher;

    public void sendSimpleMessage(String toAddress, String subjectText, String textBody) throws SendFailedException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(toAddress);
        message.setSubject(subjectText);
        message.setText(textBody);
        emailDispatcher.send(message);
    }
}
