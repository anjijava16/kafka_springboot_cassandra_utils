package com.mmtech.springkafka.data.entity;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table("employees")
@ApiModel(description="All details about the Employee. ")
public class Employee {

	@ApiModelProperty(notes = "The database generated employee ID")
	private long id;

	@ApiModelProperty(notes = "The employee first name")
	private String firstName;

	@ApiModelProperty(notes = "The employee last name")
	private String lastName;

	@ApiModelProperty(notes = "The employee email id")
	private String emailId;

	public Employee() {

	}

	public Employee(String firstName, String lastName, String emailId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	@PrimaryKey
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ "]";
	}


	public String toJsonString() {
		return "{ id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId+"}";
	}
}