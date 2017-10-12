package com.jgg.sdp.rules.objects;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RuleItem {

	private long      idGroup;
	private long      idItem;
	private String    clave;
	private String    uid;
	private Timestamp tms;
	
	private ArrayList<RuleRule> rules = new ArrayList<RuleRule>();
	
	public long getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(long idGroup) {
		this.idGroup = idGroup;
	}
	public long getIdItem() {
		return idItem;
	}
	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Timestamp getTms() {
		return tms;
	}
	public void setTms(Timestamp tms) {
		this.tms = tms;
	}
	
	
	public void addRule(RuleRule rule) {
	    rules.add(rule);	
	}
	
	public ArrayList<RuleRule> getRules() {
		return rules;
	}
}
