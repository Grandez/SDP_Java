package com.jgg.sdp.module.status;

import java.util.*;

public class StatusGroup {

	private Integer id;
	
	private HashMap<Integer, StatusItem> items = new HashMap<Integer, StatusItem>();
	
	public StatusGroup(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public StatusItem getItem(int id) {
		return items.get(id);
	}
	
	public List<StatusItem> getItems() {
		return new ArrayList<StatusItem>(items.values());
	}

	public StatusItem add(int id, StatusItem item) {
		items.put(id,  item);
		return item;
	}
	
}
