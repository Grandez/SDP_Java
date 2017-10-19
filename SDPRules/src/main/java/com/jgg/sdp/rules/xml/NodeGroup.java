package com.jgg.sdp.rules.xml;

import java.util.HashMap;

import com.jgg.sdp.rules.xml.jaxb.Group;

public class NodeGroup {

	private HashMap<String, Object> items = new HashMap<String,Object>();
	
	public NodeGroup(Group xmlGroup) {
		items.put("name", xmlGroup.getName());
	}
}
