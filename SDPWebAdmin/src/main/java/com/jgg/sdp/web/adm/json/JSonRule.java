package com.jgg.sdp.web.adm.json;

import java.sql.Timestamp;
import java.util.ArrayList;

public class JSonRule{

	private ArrayList<JSonRule>     children = new ArrayList<JSonRule>();
	private ArrayList<JSonRuleCond> conds    = new ArrayList<JSonRuleCond>();
	private JSonRuleSample          sample   = null;
	
	private Long      idParent;
	private Long      idGroup;
	private Long      idItem;
	private Long      idRule;
	private Long      idDesc;
	private Long      idCond;
	private Long      idMsg;
	private Long      idTitle;
	private Long      idSample;
	private Boolean   active;
	private Long      status;
	private Integer   priority;
	private Integer   severity;
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
	
	
	private ArrayList<String>  descRaw;

	public JSonRule() {
		
	}
	
	public JSonRule(JSonRule from) {
		if (from.getIdParent() != null) idParent   = new Long(from.getIdParent());
		if (from.getIdGroup()  != null) idGroup    = new Long(from.getIdGroup());
		if (from.getIdItem()   != null) idItem     = new Long(from.getIdItem());
		if (from.getIdRule()   != null) idRule     = new Long(from.getIdRule());
		if (from.getIdDesc()   != null) idDesc     = new Long(from.getIdDesc());
		if (from.getIdCond()   != null) idCond     = new Long(from.getIdCond());
		if (from.getIdMsg()    != null) idMsg      = new Long(from.getIdMsg());
		if (from.getIdTitle()  != null) idTitle    = new Long(from.getIdTitle());
		if (from.getIdSample() != null) idSample   = new Long(from.getIdSample());
		if (from.getActive()   != null) active     = new Boolean(from.getActive());
		if (from.getStatus()   != null) status     = new Long(from.getStatus());
		if (from.getPriority() != null) priority   = new Integer(from.getPriority());
		if (from.getPriority() != null) severity   = new Integer(from.getSeverity());
		if (from.getSeverity() != null) prefix     = from.getPrefix();
		if (from.getPrefix()   != null) uid        = from.getUid();
		if (from.getTms()      != null) tms        = new Timestamp(from.getTms().getTime());

	}

	public ArrayList<JSonRule> getChildren() {
		return children;
	}


	public void setChildren(ArrayList<JSonRule> children) {
		this.children = children;
	}


	public ArrayList<JSonRuleCond> getConds() {
		return conds;
	}


	public void setConds(ArrayList<JSonRuleCond> conds) {
		this.conds = conds;
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


	public Long getIdRule() {
		return idRule;
	}


	public void setIdRule(Long idRule) {
		this.idRule = idRule;
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

}
