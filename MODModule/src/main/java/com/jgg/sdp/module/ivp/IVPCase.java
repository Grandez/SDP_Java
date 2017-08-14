package com.jgg.sdp.module.ivp;

public class IVPCase {

	private String group = "default";
	private String object;
	private String method;
	private String value;
	private String operator;
	private String description = "No description";
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getValue() {
		return value;
	}

	public int getValueInteger() {
		return Integer.parseInt(value);
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public void setComponent(String component) {
		String[] toks = component.split(".");
		object = toks[0];
		method = toks[1];
	}
	
}
