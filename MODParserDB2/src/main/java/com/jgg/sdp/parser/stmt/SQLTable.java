package com.jgg.sdp.parser.stmt;

public class SQLTable {
	
	private String  name;
	private String  alias;
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
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
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
