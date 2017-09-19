package com.jgg.sdp.web.persister.json;

import java.sql.Timestamp;

public class MemberInfo {
	private String name;
	private int    type;
	private String uid;
	private int    modules;
	private String firma;
	private String source;
	private Timestamp tms;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getModules() {
		return modules;
	}
	public void setModules(int modules) {
		this.modules = modules;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Timestamp getTms() {
		return tms;
	}
	public void setTms(Timestamp tms) {
		this.tms = tms;
	}
	
}
