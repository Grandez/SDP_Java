package com.jgg.sdp.rules.objects;

import java.util.*;

import com.jgg.sdp.tools.Numero;

public class RuleGroup {
	private Long   id;
	private Long   idParent;
	private Long   activo;
	private String name;
	private String prefix; 
	
	private ArrayList<RuleItem> items = new ArrayList<RuleItem>();
	private HashMap<Integer, Integer> keyNum = new HashMap<Integer, Integer>();
	private HashMap<String, Integer> keyTxt = new HashMap<String, Integer>();
	
	private RuleGroup parent = null;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public RuleGroup getParent() {
		return parent;
	}
	public void setParent(RuleGroup parent) {
		this.parent = parent;
	}
	public long getIdParent() {
		return idParent;
	}
	public void setActivo(Long activo) {
		this.activo = activo;
	}
	public Long getActivo() {
		return activo;
	}
	public void setIdParent(long idParent) {
		this.idParent = idParent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
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
	
	@Override
	public String toString() {
		return name;
	}
}
