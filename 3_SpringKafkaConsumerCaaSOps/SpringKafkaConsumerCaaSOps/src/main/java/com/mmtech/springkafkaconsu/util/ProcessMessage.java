package com.mmtech.springkafkaconsu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mmtech.springkafkaconsu.data.entity.Employee;
import com.mmtech.springkafkaconsu.data.repostitory.EmployeeRepository;

/**
 * 
 * 
 */
@Component
public class ProcessMessage {
	Logger LOGGER = LoggerFactory.getLogger(ProcessMessage.class);

		
	@Autowired
	EmployeeRepository employeeService;

	public boolean procesMessage(String message) {
		LOGGER.info("inside Process Messge class");
		boolean flag = false;
		try {
			Gson gson = new Gson();
			Employee empData = gson.fromJson(message, Employee.class);
			System.out.println(" In Cassandra Consumer Side logic Here ===>"+empData.toString());
				employeeService.save(empData);
			flag = true;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			flag = false;
		}
		return flag;
	}

}