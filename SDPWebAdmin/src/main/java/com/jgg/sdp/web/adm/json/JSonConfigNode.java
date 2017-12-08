package com.jgg.sdp.web.adm.json;

import java.util.ArrayList;

public class JSonConfigNode {
//	private String  id;
//	private String  parent;
	private String  text;

	private JSonConfigData data;
	
	private ArrayList<JSonConfigNode> children = new ArrayList<JSonConfigNode>();
/*	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
*/	
/*	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
*/	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public JSonConfigData getData() {
		return data;
	}
	public void setData(JSonConfigData data) {
		this.data = data;
	}
	public ArrayList<JSonConfigNode> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<JSonConfigNode> children) {
		this.children = children;
	}
	public void addChildren(JSonConfigNode children) {
		this.children.add(children);
	}
	public JSonConfigNode getChildrenNode(String key) {
		for (int idx = 0; idx < children.size(); idx++) {
			if (children.get(idx).getText().compareTo(key) == 0) {
				return children.get(idx);
			}
		}
		return null;
	}
}
