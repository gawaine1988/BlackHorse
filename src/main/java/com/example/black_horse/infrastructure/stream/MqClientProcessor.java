package com.example.black_horse.infrastructure.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MqClientProcessor {
    String INPUT = "myInput";

    @Input
    SubscribableChannel myInput();


    @Output("myOutput")
    MessageChannel anOutput();

    @Output("compensateOutput")
    MessageChannel compensateOutPut();

}
