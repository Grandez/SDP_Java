package com.jgg.sdp.ivp.cases;

import java.util.*;

import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.module.ivp.IVPCase;

public class BlockCases {

	private ArrayList<IVPCase> cases = new ArrayList<IVPCase>();
	private ArrayList<Archivo> archivos = new ArrayList<Archivo>();
	
	public List<IVPCase> getCases() {
		return cases;
	}
	
	public void addCases(List<IVPCase> cases) {
		this.cases.addAll(cases);
	}
	
}
