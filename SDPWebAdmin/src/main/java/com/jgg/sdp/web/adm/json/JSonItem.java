package com.jgg.sdp.web.adm.json;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jgg.sdp.domain.rules.IRule;

public class JSonItem implements IJSonText {

	private ArrayList<JSonRule>     rules  = new ArrayList<JSonRule>();
	private ArrayList<JSonRuleCond> activations  = new ArrayList<JSonRuleCond>();
	private JSonRuleSample          sample = null;
	
	private Long      idParent;
	private Long      idGroup;
	private Long      idItem;

	private Long      idDesc;
	private Long      idCond;
	private Long      idMsg;
	private Long      idTitle;
	private Long      idSample;
	private Boolean   active;
	private Long      status;
	private String    prefix;
	private String    uid;
	private Timestamp tms;

	private String  name;
	private String  title;
	private String  description;
	private String  message;
	
	private String  id;
	private String  parent;
	private int     nodeType;
	
	
	private Boolean expanded = false;
	
	private ArrayList<String>  descRaw;

	public JSonItem(IRule obj) {
    	idGroup  = new Long(obj.getIdGroup());
    	idDesc   = obj.getIdDesc()   == null ? 0L : new Long(obj.getIdDesc());
    	idTitle  = obj.getIdTitle()  == null ? 0L : new Long(obj.getIdTitle());
    	idMsg    = obj.getIdMsg()    == null ? 0L : new Long(obj.getIdMsg());
    	idSample = obj.getIdSample() == null ? 0L : new Long(obj.getIdSample());
    	
    	name   = obj.getName();
        active = (obj.getActive() < 0 ? false : true);
        status = obj.getActive();
        uid    = obj.getUid();
        tms    = obj.getTms();
        
	}
	
//	public JSonItem(JSonItem from) {
//		if (from.getIdParent() != null) idParent   = new Long(from.getIdParent());
//		if (from.getIdGroup()  != null) idGroup    = new Long(from.getIdGroup());
//		if (from.getIdItem()   != null) idItem     = new Long(from.getIdItem());
//		if (from.getIdDesc()   != null) idDesc     = new Long(from.getIdDesc());
//		if (from.getIdCond()   != null) idCond     = new Long(from.getIdCond());
//		if (from.getIdMsg()    != null) idMsg      = new Long(from.getIdMsg());
//		if (from.getIdTitle()  != null) idTitle    = new Long(from.getIdTitle());
//		if (from.getIdSample() != null) idSample   = new Long(from.getIdSample());
//		if (from.getActive()   != null) active     = new Boolean(from.getActive());
//		if (from.getStatus()   != null) status     = new Long(from.getStatus());
//		if (from.getPrefix()   != null) uid        = from.getUid();
//		if (from.getTms()      != null) tms        = new Timestamp(from.getTms().getTime());
//
//	}

	public ArrayList<JSonRule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<JSonRule> rules) {
		this.rules.addAll(rules);
	}

	public void addRule(JSonRule rule) {
		rules.add(rule);
	}

	public ArrayList<JSonRuleCond> getActivations() {
		return activations;
	}


	public void setActivations(List<JSonRuleCond> activations) {
		this.activations.addAll(activations);
	}


	public JSonRuleSample getSample() {
		return sample;
	}


	public void setSample(JSonRuleSample sample) {
		this.sample = sample;
	}


	public Long getIdParent() {
		return idParent;
	}


	public void setIdParent(Long idParent) {
		this.idParent = idParent;
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

	public Long getIdDesc() {
		return idDesc;
	}


	public void setIdDesc(Long idDesc) {
		this.idDesc = idDesc;
	}


	public Long getIdCond() {
		return idCond;
	}


	public void setIdCond(Long idCond) {
		this.idCond = idCond;
	}


	public Long getIdMsg() {
		return idMsg;
	}


	public void setIdMsg(Long idMsg) {
		this.idMsg = idMsg;
	}


	public Long getIdTitle() {
		return idTitle;
	}


	public void setIdTitle(Long idTitle) {
		this.idTitle = idTitle;
	}


	public Long getIdSample() {
		return idSample;
	}


	public void setIdSample(Long idSample) {
		this.idSample = idSample;
	}


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getParent() {
		return parent;
	}


	public void setParent(String parent) {
		this.parent = parent;
	}


	public int getNodeType() {
		return nodeType;
	}


	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}


	public ArrayList<String> getDescRaw() {
		return descRaw;
	}


	public void setDescRaw(ArrayList<String> descRaw) {
		this.descRaw = descRaw;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}


	public void merge(JSonItem parent) {
	   if (idDesc == 0)    idDesc   = parent.getIdDesc();
	   if (idMsg  == 0)    idMsg    = parent.getIdMsg();
	   if (idTitle == 0)   idTitle  = parent.getIdTitle();
	   if (idSample == 0)  idSample = parent.getIdSample();
	   
	   if (title == null)  title    = parent.getTitle();
	   if (prefix == null) prefix   = parent.getPrefix();
	}
}
