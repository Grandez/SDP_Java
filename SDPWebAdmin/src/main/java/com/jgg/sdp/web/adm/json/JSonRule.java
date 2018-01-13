package com.jgg.sdp.web.adm.json;

import java.util.ArrayList;
import java.util.List;

import com.jgg.sdp.domain.rules.RULRule;

public class JSonRule {

	private Long      idRule;
	private Integer   priority;
	private Integer   severity;
    private String    label;
    private Boolean   active; 
    private Long      idMsg;
    private String    name;
    
	private JSonRuleSample          sample = null;
	
	private String  message;
	
	private ArrayList<JSonRuleCond> activations = new ArrayList<JSonRuleCond>();
	private JSonRuleCond            condition   = null;
	
	public JSonRule(RULRule r) {
		idRule   = new Long(r.getIdRule());  
		priority = new Integer(r.getPriority());
		severity = new Integer(r.getSeverity());
        idMsg    = (r.getIdMsg() == null) ? 0L : new Long(r.getIdMsg());
        name     = r.getName();
	}
	
	public Long getIdRule() {
		return idRule;
	}

	public void setIdRule(Long idRule) {
		this.idRule = idRule;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<JSonRuleCond> getActivations() {
		return activations;
	}

	public void setActivations(List<JSonRuleCond> activations) {
		this.activations.addAll(activations);
	}

	public JSonRuleCond getCondition() {
		return condition;
	}

	public void setCondition(JSonRuleCond cond) {
		this.condition = cond;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	public Long getIdMsg() {
		return idMsg;
	}
    public void setIdMsg(Long idMsg) {
    	this.idMsg = idMsg;
    }
	
}
