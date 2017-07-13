package com.jgg.sdp.module.ivp;

import java.util.*;

public class IVPCase {

	private String group = "default";
	private String object;
	private String method;
	private String value;
	private String description = "No description";
	
	private ArrayList<String> words = new ArrayList<String>();
    private StringBuilder str = new StringBuilder();
    
	
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
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

	public void addWord(String txt) {
		words.add(txt);
	}

	public void addDescription(String txt) {
		str.append(txt);
	}
	
	public void endCase() {
		int idx = 0;
        description = str.toString().trim();
        if (words.size() > 3) {
        	group = words.get(0);
        	idx = 1;
        }

        object = words.get(idx++);
    	method = words.get(idx++);
    	value =  words.get(idx++);
	}
}
