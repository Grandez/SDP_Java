package com.jgg.sdp.rules.objects;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RuleItem {

	private Long      idGroup;
	private Long      idItem;
	private String    object;
	private String    uid;
	private String    prefix;
	private Timestamp tms;
	
	private RuleCond  activation = null;
	
	private ArrayList<RuleRule> rules = new ArrayList<RuleRule>();
	
	public RuleItem() {
		
	}
	public RuleItem(RuleItem item) {
		this.idGroup    = item.getIdGroup();
		this.idItem     = item.getIdItem();
		this.object     = item.getObject();
		this.activation = item.getActivation();
		this.prefix     = item.getPrefix();
	}
	
	public Long getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(long idGroup) {
		this.idGroup = idGroup;
	}
	public Long getIdItem() {
		return idItem;
	}
	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getUid() {
		return uid;
	}
	public RuleCond getActivation() {
		return activation;
	}
	public void setActivation(RuleCond cond) {
		this.activation = cond;
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
