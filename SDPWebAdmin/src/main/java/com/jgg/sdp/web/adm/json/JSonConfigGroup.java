package com.jgg.sdp.web.adm.json;

public class JSonConfigGroup {
	private int id;
	private String name;
	private String idDiv;

	public JSonConfigGroup(int id, String name) {
		this.id = id;
		this.name = name;
		this.idDiv = "cfgGrid" + id;
	}

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

	public String getIdDiv() {
		return idDiv;
	}

	public void setIdDiv(String idDiv) {
		this.idDiv = idDiv;
	}
	
	
}
