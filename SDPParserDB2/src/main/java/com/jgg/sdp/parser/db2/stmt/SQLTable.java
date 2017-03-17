package com.jgg.sdp.parser.db2.stmt;

public class SQLTable {
	
	private String  name;
	private boolean temporary;
	private int     references = 0;
	
	public SQLTable(String name) {
		this.name = name;
		temporary = false;
	}
	public SQLTable(String name, boolean temporary) {
		this.name = name;
		this.temporary = temporary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}
	public boolean isTemporary() {
		return temporary;
	}
	public void incReferences() {
		references++;
	}
	public int getReferences() {
		return references;
	}
	
}
