package com.jgg.sdp.module.tables;

import java.util.*;

import com.jgg.sdp.module.items.CICSVerb;

public class TBCics {

	private HashMap<String, CICSVerb> verbos = new HashMap<String, CICSVerb>();
	
	public void add(String verbo, int type) {
		String key = type + verbo;
		
		CICSVerb v = verbos.get(key);
		if (v == null) v = new CICSVerb(verbo, type);
		v.inc();
		verbos.put(key,  v);
		
	}
	
	public ArrayList<CICSVerb> getList() {
		return new ArrayList<CICSVerb>(verbos.values());
	}

}
