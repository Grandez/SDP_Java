package com.jgg.sdp.rules.objects;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RuleItem {

	private Long      idGroup;
	private Long      idItem;
	private String    name;
	private String    object;
	private Integer   type;
	private String    uid;
	private String    prefix;
	private Timestamp tms;
	
	private ArrayList<RuleCond>  activations = new ArrayList<RuleCond>();
	
	private ArrayList<RuleRule> rules = new ArrayList<RuleRule>();
	
	public RuleItem() {
		
	}
	public RuleItem(RuleItem item) {
		this.idGroup     = item.getIdGroup();
		this.idItem      = item.getIdItem();
		this.name        = item.getName();		
		this.object      = item.getObject();
		this.type        = item.getType();
		this.activations.addAll(item.getActivations());
		this.prefix      = item.getPrefix();
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
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getType() {
		return type;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public ArrayList<RuleCond> getActivations() {
		return activations;
	}
	public void setActivations(ArrayList<RuleCond> conds) {
		this.activations.addAll(conds);
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

	@Override
	public String toString() {
		return name;
	}
}
