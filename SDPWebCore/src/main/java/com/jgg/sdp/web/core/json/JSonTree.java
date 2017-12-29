package com.jgg.sdp.web.core.json;

import java.util.ArrayList;

public class JSonTree<T> {

	private String        id;
	private String        parent;
	private String        text;
	private String        icon;
	private JSonTreeState state = new JSonTreeState();

	private ArrayList<JSonTree<T>> children = null; 

	private T             data;
	
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public ArrayList<JSonTree<T>> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<JSonTree<T>> children) {
		this.children = children;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public JSonTreeState getState() {
		return state;
	}
	public void setState(JSonTreeState state) {
		this.state = state;
	}

	public void addChildren(JSonTree<T> c) {
		if (children == null) children = new ArrayList<JSonTree<T>>();
		children.add(c);
	}
}
