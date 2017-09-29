package com.jgg.sdp.ivp.items;

public class IVPAction {
	
	public static final int NONE = 0;
	public static final int SQL_STMT   = 10;
	public static final int SQL_SCRIPT = 11;
	
	private int type;
	private String value;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
