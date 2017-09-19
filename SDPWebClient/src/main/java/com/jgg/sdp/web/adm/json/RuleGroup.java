package com.jgg.sdp.web.adm.json;

import java.util.*;

public class RuleGroup {
	private Integer id;
	private Integer idGroup;
	private Integer idParent;
	private Integer active;
	private Integer idDesc;
    private String  prefix;
    private String  desc;

    private ArrayList<RuleItem> items0 = new ArrayList<RuleItem>();
    private ArrayList<RuleItem> items1 = new ArrayList<RuleItem>();
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    
    public Integer getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	public Integer getIdParent() {
		return idParent;
	}
	public void setIdParent(Integer idParent) {
		this.idParent = idParent;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public Integer getIdDesc() {
		return idDesc;
	}
	public void setIdDesc(Integer idDesc) {
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
