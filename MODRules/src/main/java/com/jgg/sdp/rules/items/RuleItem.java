package com.jgg.sdp.rules.items;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RuleItem {

	private int       idGroup;
	private int       idItem;
	private String    clave;
	private String    uid;
	private Timestamp tms;
	
	private ArrayList<RuleRule> rules = new ArrayList<RuleRule>();
	
	public int getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
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
