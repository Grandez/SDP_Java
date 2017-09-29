package com.jgg.sdp.ivp.items;

import java.util.*;

public class Case {

	private String name = "";
	private String description;
	private ArrayList<String> modules  = new ArrayList<String>();
	
	public Case() {
		
	}
	
	// Constructor de copia
	public Case(Case c) {
		this.name = c.getName();
		this.description = c.getDescription();
	}
	
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
