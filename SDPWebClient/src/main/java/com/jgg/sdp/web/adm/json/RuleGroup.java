package com.jgg.sdp.web.adm.json;

import java.util.*;

public class RuleGroup {
	private Long    id;
	private Long    idGroup;
	private Long    idParent;
	private Long    active;
	private Long    idDesc;
    private String  prefix;
    private String  desc;

    private ArrayList<RuleItem> items0 = new ArrayList<RuleItem>();
    private ArrayList<RuleItem> items1 = new ArrayList<RuleItem>();
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
    public Long getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
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
	public void setActive(long active) {
		this.active = active;
	}
	public Long getIdDesc() {
		return idDesc;
	}
	public void setIdDesc(Long idDesc) {
		this.idDesc = idDesc;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void addItem0(RuleItem item) {
		items0.add(item);
	}
	
	public List<RuleItem> getItems0() {
		return items0;
	}
	
	public void addItem1(RuleItem item) {
		items1.add(item);
	}
	
	public List<RuleItem> getItems1() {
		return items1;
	}

}
