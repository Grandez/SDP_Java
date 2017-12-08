/**
 * Representa una unidad de compilacion
 */
package com.jgg.sdp.common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SDPUnit implements Serializable {
	
	private static final long serialVersionUID = 4070069425718410603L;

	private ArrayList<SDPMember> sources = new ArrayList<SDPMember>();
	
	private String    name;
	private Timestamp tms;
	private String    uid;
	
	public SDPUnit(String name) {
		this.name = name;
		this.tms = new Timestamp(System.currentTimeMillis());
		this.uid = System.getProperty("user.name");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addMember(SDPMember member) {
		sources.add(member);
	}

	public List<SDPMember> getMembers() {
		return sources;
	}

	public Timestamp getTms() {
		return tms;
	}

	public void setTms(Timestamp tms) {
		this.tms = tms;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
