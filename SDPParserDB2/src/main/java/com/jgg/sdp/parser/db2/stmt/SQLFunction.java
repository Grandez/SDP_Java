package com.jgg.sdp.parser.db2.stmt;

public class SQLFunction {

	private String name;
	private int    type;
	private int    weight;
	private int    references;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getReferences() {
		return references;
	}
	public void setReferences(int references) {
		this.references = references;
	}
	
	
}
