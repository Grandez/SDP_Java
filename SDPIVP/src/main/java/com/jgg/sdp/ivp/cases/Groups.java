package com.jgg.sdp.ivp.cases;

import java.util.*;
import java.util.Map.Entry;

import com.jgg.sdp.module.ivp.IVPCase;

public class Groups {

	private HashMap<Integer, ArrayList<IVPCase>> groups = new HashMap<Integer, ArrayList<IVPCase>>();
	
	
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
	
	public List<IVPCase> getCases(int group) {
		ArrayList<IVPCase> l = groups.get(group);
		if (l == null) return new ArrayList<IVPCase>();
		return l;
	}
	
	public List<Integer> getGroups() {
		ArrayList<Integer> grupos = new ArrayList<Integer>();
		for (Map.Entry<Integer, ArrayList<IVPCase>> entry : groups.entrySet()) {
		    grupos.add(entry.getKey());
		}
		return grupos;
	}
	
}
