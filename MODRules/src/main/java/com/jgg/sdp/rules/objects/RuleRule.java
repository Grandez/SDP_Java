package com.jgg.sdp.rules.objects;

public class RuleRule  extends RuleItem {

	private Long      idRule;
	private int       severity;
	private int       priority;	
	
	private RuleCond  condition  = null;
	
	public RuleRule() {
	}
	
	public RuleRule(RuleItem item) {
		super(item);
		this.type = 2;
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
	public RuleCond getCondition() {
		return condition;
	}
	public void setCondition(RuleCond condition) {
		this.condition = condition;
	}
	
}
