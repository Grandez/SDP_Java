package com.jgg.sdp.domain.services;

public class DBField {
	private String name = "NONAME";
	private Object value = null;
	private Integer type;
	private Integer size;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public String getValueAsString() {

		if (value  instanceof String) {
			return (String) value;
		} else if (value instanceof Integer) { 
			return ((Integer) value).toString();
		} else if (value instanceof Long) {
			return ((Integer) value).toString();
		} 
		
		return value.toString();
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	public void setSQLType(Integer type) {
		this.type = type;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}


	public boolean isInteger() {
		return (value instanceof Integer || value instanceof Long); 
	}
}
