package com.jgg.sdp.ivp.data;

public class Case {

	private String name;
	private int grupo;
	private String newHash;
	private String xmlHash;
	
	public Case(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getGrupo() {
		return grupo;
	}
	
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public String getNewHash() {
		return newHash;
	}

	public void setNewHash(String newHash) {
		this.newHash = newHash;
	}

	public String getXMLHash() {
		return xmlHash;
	}

	public void setXMLHash(String xmlHash) {
		this.xmlHash = xmlHash;
	}
	
	
	
	
	
}
