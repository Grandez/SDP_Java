package com.jgg.sdp.module.ivp;

import java.util.*;

public class TBIVPCases {

	ArrayList<IVPCase> cases = new ArrayList<IVPCase>();
	
	public void addCase(IVPCase c) {
		cases.add(c);
	}
	
	public List<IVPCase> getCases() {
		return cases;
	}
}
