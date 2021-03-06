package com.jgg.sdp.rules.objects;

import java.util.ArrayList;

public class RuleItem extends RuleGroup {

	protected Long      idItem;
	protected String    object;
	
	private ArrayList<RuleRule> rules = new ArrayList<RuleRule>();
	
	public RuleItem() {}
	public RuleItem(RuleGroup group) {
		super(group);
		this.nodeType = 1;
		this.prefix = group.getPrefix();
	}
	public RuleItem(RuleItem item) {
		super(item);
		idItem = new Long(item.getIdItem());
		object = item.getObject();
		nodeType = 1;
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

	public void addRule(RuleRule rule) {
	    rules.add(rule);	
	}	
	public ArrayList<RuleRule> getRules() {
		return rules;
	}

	@Override
	public String toString() {
		return idGroup + "-" + idItem;
	}
}
