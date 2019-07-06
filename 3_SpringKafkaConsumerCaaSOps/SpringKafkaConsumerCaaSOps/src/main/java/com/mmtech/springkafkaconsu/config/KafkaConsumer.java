package com.mmtech.springkafkaconsu.config;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

import com.mmtech.springkafkaconsu.util.ProcessMessage;

/**
 * Created by a522467 on 11/17/16.
 * For testing convenience, added a CountDownLatch.
 * This allows the POJO to signal that a message is received.
 * This is something you are not likely to implement in a production application
 */
public class KafkaConsumer {
	
    @Autowired
    ProcessMessage processMessage;

    
    @Value("${spark.kafka.consumer.topic_name}")
	private  String topicName;
    
    private final String topicNameId=topicName;
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(2);

    @KafkaListener(topics = "dev-kafka-topic")
    public void receiveMessage(String message) {
        LOGGER.info("received message='{}'", message);
       // processMessage = new ProcessMessage();
       boolean flag =  processMessage.procesMessage(message);
        LOGGER.info("message has been processed ?"+flag);
        LOGGER.info("message has been processed successfully="+message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}