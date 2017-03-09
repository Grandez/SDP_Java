package com.jgg.sdp.domain;

public class SQLField {
	
	private String name;
	private Object value;
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

	
}
