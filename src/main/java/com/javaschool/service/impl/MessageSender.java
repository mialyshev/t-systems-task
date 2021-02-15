package com.javaschool.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSender {

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    public void sendMessage() {
        MessageCreator messageCreator = session -> session.createTextMessage("update");
        jmsTemplate.send(messageCreator);
    }
}