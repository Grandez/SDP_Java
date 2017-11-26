package com.jgg.sdp.rules.objects;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RuleRule extends RuleItem {

	private String    itemName;
	private Long      idGroup;
	private Long      idItem;
	private Long      idRule;
	private int       severity;
	private int       priority;	
	private String    prefix;
	private String    uid;
	private Timestamp tms;
	
	private ArrayList<RuleCond>  activations = new ArrayList<RuleCond>();
	private RuleCond             condition  = null;
	
	public RuleRule(RuleItem item) {
		super(item);
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}
	public Long getIdItem() {
		return idItem;
	}
	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}
	public Long getIdRule() {
		return idRule;
	}
	public void setIdRule(Long idRule) {
		this.idRule = idRule;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
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
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Timestamp getTms() {
		return tms;
	}
	public void setTms(Timestamp tms) {
		this.tms = tms;
	}
	public ArrayList<RuleCond> getActivation() {
		return activations;
	}
	public void setActivations(ArrayList<RuleCond> activations) {
		this.activations.addAll(activations);
	}
	public RuleCond getCondition() {
		return condition;
	}
	public void setCondition(RuleCond condition) {
		this.condition = condition;
	}
	
	
}
