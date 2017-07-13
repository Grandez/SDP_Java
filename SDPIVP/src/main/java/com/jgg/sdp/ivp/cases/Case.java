package com.jgg.sdp.ivp.cases;

import java.util.*;

public class Case {

	private String name = "";
	private String description;
	private ArrayList<String> modules = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void addModules(String mod) {
		modules.add(mod);
	}
	public List<String> getModules() {
		return modules;
	}
	
}
