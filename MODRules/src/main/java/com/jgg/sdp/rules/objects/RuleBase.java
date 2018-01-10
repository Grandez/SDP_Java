package com.jgg.sdp.rules.objects;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RuleBase {

	protected Long    id;
	protected Long    idParent;
	protected Long    active;
	protected Long    idTitle;
	protected Long    idDesc;
	protected Long    idMsg;
	protected String  prefix;
	protected String  name;
	protected String  title;
	protected String  description;
	protected String  message;
	protected Integer type;
	
	protected String    uid;
	protected Timestamp tms;
	
	protected Integer   nodeType;

	protected ArrayList<RuleCond>  activations = new ArrayList<RuleCond>();
	
	public RuleBase() {
		
	}
	public RuleBase(RuleBase base) {
       id          = new Long(base.getId());
       idParent    = new Long(base.getId());
       active      = new Long(base.getId());
       idTitle     = new Long(base.getId());
       idDesc      = new Long(base.getId());
       idMsg       = new Long(base.getId());
       prefix      = base.getPrefix();
       name        = base.getName();
       title       = base.getTitle();
       description = base.getDescription();
       message     = base.getMessage();
       uid         = base.getUid();
       tms         = new Timestamp(base.getTms().getTime());
	   type        = base.getType();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdParent() {
		return idParent;
	}
	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}
	public Long getActive() {
		return active;
	}
	public void setActive(Long active) {
		this.active = active;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getIdTitle() {
		return idTitle;
	}
	public void setIdTitle(Long idTitle) {
		this.idTitle = idTitle;
	}
	public Long getIdDesc() {
		return idDesc;
	}
	public void setIdDesc(Long idDesc) {
		this.idDesc = idDesc;
	}
	public Long getIdMsg() {
		return idMsg;
	}
	public void setIdMsg(Long idMsg) {
		this.idMsg = idMsg;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getNodeType() {
		return nodeType;
	}
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
	
	public ArrayList<RuleCond> getActivations() {
		return activations;
	}
	public void setActivations(ArrayList<RuleCond> activations) {
		this.activations = activations;
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
	
	@Override
	public String toString() {
		return name;
	}

}
