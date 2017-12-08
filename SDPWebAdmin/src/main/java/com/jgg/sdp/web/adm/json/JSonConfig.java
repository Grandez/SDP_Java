package com.jgg.sdp.web.adm.json;

import java.util.ArrayList;

public class JSonConfig {

	private ArrayList<JSonConfigGroup> groups = new ArrayList<JSonConfigGroup>();
	private ArrayList<JSonConfigNode>  items  = new ArrayList<JSonConfigNode>();
	
	public void addGroup(JSonConfigGroup g) {
		groups.add(g);
	}
 
	public ArrayList<JSonConfigGroup> getGroups() {
		return groups;
	}

	public void addItem(JSonConfigNode i) {
		items.add(i);
	}
	
	public ArrayList<JSonConfigNode> getItems() {
		return items;
	}

}
