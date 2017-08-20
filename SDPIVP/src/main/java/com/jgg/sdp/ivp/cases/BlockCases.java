package com.jgg.sdp.ivp.cases;

import java.util.*;

public class BlockCases {

	private ArrayList<Case> cases = new ArrayList<Case>();
	
	public int addCase(Case c) {
		for (int idx = 0; idx < cases.size(); idx++) {
			if (cases.get(idx).getName().compareTo(c.getName()) == 0) return idx;
		}
		cases.add(new Case(c));
		return cases.size() - 1;
	}
	
	public void addModule(int c, String module) {
		cases.get(c).addModules(module);
	}
	
	public List<Case> getCases() {
		return cases;
	}
	
	public void addCases(List<Case> cases) {
		this.cases.addAll(cases);
	}
	
}
