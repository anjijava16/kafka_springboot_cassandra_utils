package com.mmtech.mmpocspringcass.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mmtech.mmpocspringcass.data.entity.ProcessEmployee;
import com.mmtech.mmpocspringcass.data.repository.ProcessEmployeeRepository;

	
@RestController
public class ProcessEmployeeController
{
	@Autowired
	ProcessEmployeeRepository employeeRepository;

	@GetMapping(value = "/healthcheck", produces = "application/json; charset=utf-8")
	public String getHealthCheck()
	{
		return "{ \"isWorking\" : true }";
	}

	@GetMapping("/employees")
	public List<ProcessEmployee> getEmployees()
	{
		Iterable<ProcessEmployee> result = employeeRepository.findAll();
		List<ProcessEmployee> employeesList = new ArrayList<ProcessEmployee>();
		result.forEach(employeesList::add);
		return employeesList;
	}

//	
//	@GetMapping("/employee/{id}")
//	public Optional<Employee> getEmployee(@PathVariable String id)
//	{
//		
//		Optional<Employee> emp = employeeRepository.findById(id);
//		return emp;
//	}
//
//	@PutMapping("/employee/{id}")
//	public Optional<Employee> updateEmployee(@RequestBody Employee newEmployee, @PathVariable String id)
//	{
//		Optional<Employee> optionalEmp = employeeRepository.findById(id);
//		if (optionalEmp.isPresent()) {
//			Employee emp = optionalEmp.get();
//			emp.setFirstName(newEmployee.getFirstName());
//			emp.setLastName(newEmployee.getLastName());
//			emp.setEmail(newEmployee.getEmail());
//			employeeRepository.save(emp);
//		}
//		return optionalEmp;
//	}
//	
//		
//	@DeleteMapping(value = "/employee/{id}", produces = "application/json; charset=utf-8")
//	public String deleteEmployee(@PathVariable String id) {
//Boolean result = employeeRepository.existsById(id);
////		employeeRepository.deleteById(id);
////		employeeRepository.delete(id);
////		Boolean result =true;
//		return "{ \"success\" : "+ (result ? "true" : "false") +" }";
//	}
//
//	@PostMapping("/employee")
//	public Employee addEmployee(@RequestBody Employee newEmployee)
//	{
//		String id = String.valueOf(new Random().nextInt());
//		Employee emp = new Employee(id, newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getEmail());
//		employeeRepository.save(emp);
//		return emp;
//	}
}
