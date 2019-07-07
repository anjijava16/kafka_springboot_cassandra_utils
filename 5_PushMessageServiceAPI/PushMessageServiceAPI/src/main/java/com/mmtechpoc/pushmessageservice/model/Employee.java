package com.mmtechpoc.pushmessageservice.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about the Employee. ")
public class Employee {

	@ApiModelProperty(notes = "The database generated employee ID")
	private long id;

	@ApiModelProperty(notes = "The employee first name")
	private String firstname;

	@ApiModelProperty(notes = "The employee last name")
	private String lastname;

	@ApiModelProperty(notes = "The employee email id")
	private String emailid;

	public Employee() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}


}