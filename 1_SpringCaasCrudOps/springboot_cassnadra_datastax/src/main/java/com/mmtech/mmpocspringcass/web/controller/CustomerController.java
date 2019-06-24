package com.mmtech.mmpocspringcass.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmtech.mmpocspringcass.data.entity.Customer;
import com.mmtech.mmpocspringcass.data.repository.CustomerRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/customers")
@Api(value = "API to perform CRUD operation in a customer database maintained in apache cassandra", description = "This API provides the capability to perform CRUD operation in a customer "
		+ "database maintained in apache cassandra", produces = "application/json")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerRepository repository;

	@ApiOperation(value = "Create a new customer", consumes = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "customer Id", required = true, dataType = "Integer", paramType = "header"),
			@ApiImplicitParam(name = "name", value = "Name of customer", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "lastname", value = "lastname of customer", required = true, dataType = "String", paramType = "query") })
	@RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
	public ResponseEntity<Object> createStudent(@RequestHeader(name = "id") Integer id, @RequestParam String name,
			@RequestParam String lastname) {
		logger.debug("Creating Student with name :: {}", name);
		ResponseEntity<Object> response = null;
		try {
			// save a couple of customers
			this.repository.save(new Customer(id, name, lastname));
			response = new ResponseEntity<Object>("Customer created successfully with Id :" + id, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@ApiOperation(value = "Search Customer by customerId", produces = "application/json")
	@RequestMapping(value = "searchCustomerId/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<Object> searchStudentById(
			@ApiParam(name = "customerId", value = "The Id of the customer to be viewed", required = true) @PathVariable String customerId) {
		logger.debug("Searching for Customer with customerId :: {}", customerId);

		String NO_RECORD = "Customer not found for Student Id : ";

		ResponseEntity<Object> response = null;
		try {
			Customer customer = this.repository.findByFirstName(customerId);
			
			System.out.println("##########################");
			System.out.println(customer.toString());
			System.out.println("##########################");
			if (customer == null) {
				response = new ResponseEntity<Object>(NO_RECORD + customerId, HttpStatus.OK);
			} else {
				response = new ResponseEntity<Object>(customer, HttpStatus.OK);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;

	}

	
	@ApiOperation(value = "Delete the Customer by customerId", produces = "application/json")
	@RequestMapping(value = "deleteCustomerId/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<Object> deleteCustomerId(	@ApiParam(name = "customerId", value = "The Id of the customer to be viewed", required = true) @PathVariable String customerId) {
		logger.debug("Delete Operation for Customer with customerId :: {}", customerId);
		String NO_RECORD = "Customer not found for Customer Id : ";
		ResponseEntity<Object> response = null;
			 try {
				 //repository.delete(customerId);
		        response = new ResponseEntity<Object>("Customer deleted successfully with Id :" + customerId, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
