package com.mooneyserver.springamqpretry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AmqpQueueConsumer {

    private static final Logger log = LoggerFactory.getLogger(AmqpQueueConsumer.class);

    @RabbitListener(queues = "my-consuming-q")
    public void handleMessage(@Payload SampleMessage message) {
        log.info("Received <{}>", message);

        switch(message.getId()) {
            case 1:
                log.info("Happy to accept 1s");
                break;
            case 2:
                log.warn("It's hard to process 2s, we want to retry");
                throw new IllegalStateException("Should be retried");
            case 3:
                log.error("3s are cunts. No retry!");
                throw new ArithmeticException("Death to 3s");
        }
    }
}