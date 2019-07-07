package com.mmtechpoc.pushmessageservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mmtechpoc.pushmessageservice.model.DataInput;
import com.mmtechpoc.pushmessageservice.model.Employee;
import com.mmtechpoc.pushmessageservice.services.KafkaProducer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(value = "API Sending MicroService Management System", description = "Operations pertaining to API Sending MicroService System")
public class PushMessageServiceController {

	@Autowired
	private KafkaProducer producer;


	@PostMapping("/sendMessage")
	@ApiOperation(value = "sendMessage Status ", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully send sendMessage"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public String createEmployee(
			@ApiParam(value = "Employee object store in database table", required = true) @Valid @RequestBody DataInput inputData) {

		producer.send(inputData.getInputKey(), inputData.getInputValue());

		return "Done";
	}

	@PostMapping("/sendText")
	@ApiOperation(value = "sendMessage Status ", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully send sendMessage"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public String textString(@ApiParam(value = "Employee object store in database table", required = true) @Valid @RequestParam(name="?view") String inputData) {

		

		return inputData;
	}
		
	@ApiOperation(value = "Add insertEmployeeRecordsIntoKafkaTopic")
	@PostMapping("/insertEmployeeRecordsIntoKafkaTopic")
	public String pushToTopic(
			@ApiParam(value = "Employee object store in insertEmployeeRecordsIntoKafkaTopic", required = true)
			@Valid @RequestBody Employee employee) {
		
		for(int i=0;i<=employee.getId();i++) {
		Gson gson = new Gson();
		employee.setId(employee.getId()+i);
		String json = gson.toJson(employee);
		System.out.println("Input Json Message ===>"+json);
		producer.send(json);
		}
		return "sendMessageToTopic";
	}
}