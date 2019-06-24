package com.mmtech.springkafka.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmtech.springkafka.services.KafkaProducer;
import com.mmtech.springkafka.storage.MessageStorage;

@RestController
@RequestMapping(value="/jsa/kafka")
public class WebRestController {
	
	@Autowired
	KafkaProducer producer;

	
	@GetMapping(value="/producer")
	public String producer(@RequestParam("data")String data){
		producer.send(data);
		
		return "Done";
	}
	
	
	@Autowired
	MessageStorage storage;
	
	@GetMapping(value="/consumer")
	public String getAllRecievedMessage(){
		String messages = storage.toString();
		storage.clear();
		
		return messages;
	}
}
