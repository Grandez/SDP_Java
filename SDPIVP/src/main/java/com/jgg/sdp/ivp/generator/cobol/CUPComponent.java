package com.jgg.sdp.ivp.generator.cobol;

import com.jgg.sdp.adt.ADTList;

public abstract class CUPComponent {

	public final static int TERMINAL     = 1;
	public final static int NON_TERMINAL = 2;
	public final static int RHS_TERMINAL = 6;
	
	protected int id;
	protected String name;
	protected String value;
	protected int    type;

	protected boolean terminal = true;
	
	protected ADTList<CUPComponent> lhs = new ADTList<CUPComponent>();	
	
	public CUPComponent(int type) {
		this.type = type;
	}
	
	public boolean isTerminal()    { return  terminal; }
	public boolean isNonTerminal() { return !terminal; }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
