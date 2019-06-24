package com.mmtech.springkafka.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mmtech.springkafka.data.entity.Employee;
import com.mmtech.springkafka.data.repositiory.EmployeeRepository;
import com.mmtech.springkafka.services.KafkaProducer;
import com.mmtech.springkafka.web.model.DataInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(value = "API Sending MicroService Management System", description = "Operations pertaining to API Sending MicroService System")
public class ApiProducerController {

	@Autowired
	private KafkaProducer producer;
		
	@Autowired
	private EmployeeRepository employeeRepository;

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
		Gson gson = new Gson();
		String json = gson.toJson(employee);

		System.out.println("Input Json Message ===>"+json);
		producer.send(json);
		return "sendMessageToTopic";
	}
}