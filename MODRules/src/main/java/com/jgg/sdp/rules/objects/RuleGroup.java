package com.jgg.sdp.rules.objects;

import java.util.*;

import com.jgg.sdp.tools.Numero;

public class RuleGroup extends RuleBase {

	protected Long idGroup;
	
	private ArrayList<RuleItem> items = new ArrayList<RuleItem>();
	private HashMap<Integer, Integer> keyNum = new HashMap<Integer, Integer>();
	private HashMap<String, Integer> keyTxt = new HashMap<String, Integer>();
	
	private RuleGroup parent = null;
	
	public RuleGroup() {
		
	}
	public RuleGroup(RuleGroup group) {
		super((RuleBase) group);
	}
	public RuleGroup(RuleItem item) {
		super((RuleBase) item);
		idGroup = new Long(item.getIdGroup());
	}
	public Long getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}
	public RuleGroup getParent() {
		return parent;
	}
	public void setParent(RuleGroup parent) {
		this.parent = parent;
	}

	public void addItem(RuleItem item) {
		items.add(item);
		int pos = items.size() - 1;
		if (Numero.isInteger(item.getObject())) {
			keyNum.put(Integer.parseInt(item.getObject()), pos);
		} else {
			keyTxt.put(item.getObject(), pos);
		}
	}
	
	public RuleItem getItemByName(String name) {
		Integer pos =  keyTxt.get(name);
		return (pos == null) ? null : items.get(pos);
	}
	
	public ArrayList<RuleItem> getItems() {
		return items;
	}
	
}
