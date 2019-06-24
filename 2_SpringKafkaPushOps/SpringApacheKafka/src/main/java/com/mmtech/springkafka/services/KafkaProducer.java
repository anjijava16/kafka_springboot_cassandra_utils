package com.mmtech.springkafka.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
	private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${spark.kafka.producer.topic}")
	String kafkaTopic = "InputTopic";

	public void send(String message) {
		log.info("sending data='{}'", message);
		kafkaTemplate.send(kafkaTopic, message);
	}

	public void send(String messageKey, String messageValue) {
		log.info("sending data='{}','{}' ", messageKey, messageValue);
		// kafkaTemplate.send(kafkaTopic, message);
		kafkaTemplate.send(kafkaTopic, messageKey, messageValue);

	}
}
