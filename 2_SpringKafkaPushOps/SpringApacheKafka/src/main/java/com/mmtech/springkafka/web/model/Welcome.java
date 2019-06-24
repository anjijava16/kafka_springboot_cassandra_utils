package com.mmtech.springkafka.web.model;

import com.google.gson.Gson;
import com.mmtech.springkafka.data.entity.Employee;

public class Welcome {

	public static String toJsonString(String id, String firstName, String lastName, String emailId) {
		return "{ id=" + id + ", firstName='" + firstName + "', lastName='" + lastName + "', emailId='" + emailId
				+ "'}";
	}

	public static void main(String[] args) {

		Gson gson = new Gson();

		Employee e = new Employee();

		e.setId(10);
		e.setFirstName("anji");
		e.setLastName("test");
		e.setEmailId("anjaispr@gmail.com");

		String json = gson.toJson(e);

		System.out.println(json);
		Employee empData = gson.fromJson(json, Employee.class);
		System.out.println(empData);
		System.out.println(empData.getId());
		System.out.println(empData.getFirstName());
		System.out.println(empData.getLastName());
		System.out.println(empData.getEmailId());

	}
}
