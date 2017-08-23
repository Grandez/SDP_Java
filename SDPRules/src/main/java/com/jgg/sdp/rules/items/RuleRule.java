package com.jgg.sdp.rules.items;

import java.sql.Timestamp;

public class RuleRule {

	private String    itemName;
	private int       idGroup;
	private int       idItem;
	private int       idRule;
	private int       type;
	private int       severity;
	private int       priority;	
	private int       comparator;
	private String    property;
	private String    valor;
	private String    uid;
	private Timestamp tms;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
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
	public int getIdRule() {
		return idRule;
	}
	public void setIdRule(int idRule) {
		this.idRule = idRule;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public int getComparator() {
		return comparator;
	}
	public void setComparator(int comparator) {
		this.comparator = comparator;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
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
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	
}
