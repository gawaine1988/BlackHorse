package com.example.black_horse.infrastructure.stream;

import com.example.black_horse.infrastructure.stream.dto.CompensateMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(MqClientProcessor.class)
public class MqProducerClient {

    private MqClientProcessor processor;

    @Autowired
    public MqProducerClient(MqClientProcessor processor) {
        this.processor = processor;
    }

    public boolean sendCompensate(CompensateMessage message) {
        try {
            return processor.compensateOutPut().send(MessageBuilder.withPayload(message).build());
        } catch (MessagingException e) {
            return false;
        }
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
}
