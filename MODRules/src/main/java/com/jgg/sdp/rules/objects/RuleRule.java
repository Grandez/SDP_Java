package com.jgg.sdp.rules.objects;

import java.sql.Timestamp;

public class RuleRule extends RuleItem {

	private String    itemName;
	private Long      idGroup;
	private Long      idItem;
	private Long      idRule;
	private int       type;
	private int       severity;
	private int       priority;	
	private int       comparator;
	private String    property;
	private String    valor;
	private String    prefix;
	private String    uid;
	private Timestamp tms;
	
	private RuleCond  activation = null;
	private RuleCond  condition  = null;
	
	public RuleRule(RuleItem item) {
		super(item);
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public long getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}
	public long getIdItem() {
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
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public RuleCond getActivation() {
		return activation;
	}
	public void setActivation(RuleCond activation) {
		this.activation = activation;
	}
	public RuleCond getCondition() {
		return condition;
	}
	public void setCondition(RuleCond condition) {
		this.condition = condition;
	}
	
	
}
