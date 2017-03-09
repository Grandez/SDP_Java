package com.jgg.sdp.excel.xls;

public class Dependencia {

	private String name;  // Nombre
	private int    veces;
	private int    callMode;  // CALL, LINK; ...
	private int    modo;      // STATIC / DYNAMIC
	
	public Dependencia(String name, int modo, int status) {
		this.name   = name;
		this.veces  = 1;
		this.callMode   = modo;
		this.modo = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVeces() {
		return veces;
	}
	public void setVeces(int veces) {
		this.veces = veces;
	}
	public void incVeces() {
		veces++;
	}
	
	public int getCallMode() {
		return callMode;
	}
	public void setCallMode(int modo) {
		this.callMode = modo;
	}
	public int getModo() {
		return modo;
	}
	public void setModo(int status) {
		this.modo = status;
	}
	
	
}
