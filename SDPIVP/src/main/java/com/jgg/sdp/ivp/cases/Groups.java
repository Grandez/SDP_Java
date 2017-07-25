package com.jgg.sdp.ivp.cases;

import java.util.*;

import com.jgg.sdp.module.ivp.IVPCase;

public class Groups {

	private HashMap<String, ArrayList<IVPCase>> groups = new HashMap<String, ArrayList<IVPCase>>();
	
	
	public void loadCases(List<IVPCase> cases) {
		for (IVPCase c : cases) {
			ArrayList<IVPCase> l = groups.get(c.getGroup());
			if (l == null) {
				l = new ArrayList<IVPCase>();
				groups.put(c.getGroup(), l);
			}
			l.add(c);
		}
	}
	
	public List<IVPCase> getCases(String group) {
		ArrayList<IVPCase> l = groups.get(group);
		if (l == null) return new ArrayList<IVPCase>();
		return l;
	}
	
}
