package com.jgg.sdp.rules;

import java.util.*;

public class RuleGroup {
	private int    id;
	private int    idParent;
	private String name;
	
	
	private ArrayList<RuleItem> items = new ArrayList<RuleItem>();
	private HashMap<Integer, Integer> keyNum = new HashMap<Integer, Integer>();
	private HashMap<String, Integer> keyTxt = new HashMap<String, Integer>();
	
	private RuleGroup parent = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RuleGroup getParent() {
		return parent;
	}
	public void setParent(RuleGroup parent) {
		this.parent = parent;
	}
	public int getIdParent() {
		return idParent;
	}
	public void setIdParent(int idParent) {
		this.idParent = idParent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addItem(RuleItem item) {
		items.add(item);
		int pos = items.size() - 1;
		if (item.getKeyNum() != 0) {
			keyNum.put(item.getKeyNum(), pos);
		}
		if (item.getKeyTxt() != null) {
			keyTxt.put(item.getKeyTxt(), pos);
		}
	}
	
	public RuleItem getItem(String name) {
		Integer pos =  keyTxt.get(name);
		return (pos == null) ? null : items.get(pos);
	}
	
	public ArrayList<RuleItem> getItems() {
		return items;
	}
}
