package com.jgg.sdp.module.status;

import java.util.*;

public class Status {

	public static final int ISSUES = 1;
	
	private HashMap<Integer, StatusGroup> groups = new HashMap<Integer, StatusGroup>();
	
	public List<StatusGroup> getGroups() {
		return new ArrayList<StatusGroup>(groups.values());
	}
	
	public StatusGroup getGroup(int group) {
		StatusGroup g = groups.get(group);
		if (g == null) groups.put(group,  new StatusGroup(group));
		return groups.get(group);
	}
	
	public StatusItem getItem(int idGroup, int idItem) {
		StatusGroup g = groups.get(idGroup);
		if (g == null) return null;
		return g.getItem(idItem);
	}
	
	public StatusItem add(int idGroup, int idItem, StatusItem item) {
		StatusGroup g = getGroup(idGroup);
		g.add(idItem,  item);
		return item;
	}
	
	public List<StatusItem> getStatusItemList() {
		ArrayList<StatusItem> list = new ArrayList<StatusItem>();
		
		for (StatusGroup g : getGroups()) {
			for (StatusItem i : g.getItems()) {
				list.add(i);	
			}
		}
		return list;
	}
}
