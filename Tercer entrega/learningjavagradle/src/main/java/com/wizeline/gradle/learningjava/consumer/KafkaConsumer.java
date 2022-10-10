package com.wizeline.gradle.learningjava.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wizeline.gradle.learningjava.model.BankAccountDTO;

@Component
public class KafkaConsumer {

    @KafkaListener(id = "sampleGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public BankAccountDTO consumeMessage(ConsumerRecord<String, List<BankAccountDTO>> cr, @Payload BankAccountDTO account) {
        System.out.println("Received: " + account.getUser());
        return account;
    }
}
