package com.mmtech.springkafkaconsu.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmtech.springkafkaconsu.services.MessageStorage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
	
@RestController
@RequestMapping("/api/v1")
@Api(value = "API Sending MicroService Management System", description = "Operations pertaining to API Sending MicroService System")
public class ApiWebController {

	
	@Autowired
	MessageStorage storage;
	
	
	@ApiOperation(value = "sendMessage Status ", response = String.class)
	@GetMapping(value="/consumer")
	public String getAllRecievedMessage(){
		String messages = storage.toString();
		storage.clear();
		
		return messages;
	}
}
