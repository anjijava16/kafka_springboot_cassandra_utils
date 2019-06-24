package com.mmtech.springkafka.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "All details about the Kafka Input. ")
public class DataInput {

	@ApiModelProperty(notes = "Input Application appId")
	private String appId;

	@ApiModelProperty(notes = "Input Application appName")
	private String appName;

	@ApiModelProperty(notes = "Input application Kafka Key inputKey")
	private String inputKey;

	@ApiModelProperty(notes = "Input application data means kafka inputValue")
	private String inputValue;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getInputKey() {
		return inputKey;
	}

	public void setInputKey(String inputKey) {
		this.inputKey = inputKey;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	@Override
	public String toString() {
		return "DataInput [appId=" + appId + ", appName=" + appName + ", inputKey=" + inputKey + ", inputValue="
				+ inputValue + "]";
	}

	
	
}