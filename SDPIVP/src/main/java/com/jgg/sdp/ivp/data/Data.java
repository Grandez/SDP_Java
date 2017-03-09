package com.jgg.sdp.ivp.data;

import java.util.*;

import com.jgg.sdp.core.tools.Propiedades;

public class Data {

	private Propiedades props = new Propiedades();
    private ArrayList<Case>[] grupos = new ArrayList[6];
    
	public void load(String resourceName) {
//		loadCases(props.loadConfiguration(resourceName)); 
	}

	public ArrayList<Case> getGrupo(int grupo) {
		return grupos[grupo];
	}
	
	private void loadCases(HashMap<String, String> map) {
        for (String key : map.keySet()) {
        	Case c = createCase(key, map.get(key));
        	int g = c.getGrupo();
        	if (grupos[g] == null) grupos[g] = new ArrayList<Case>();
        	grupos[g].add(c);
        }
	}
	
	private Case createCase(String key, String data) {
		String[] toks = data.split(";");
		Case c = new Case(key);
		c.setGrupo(Integer.parseInt(toks[0]));
		c.setNewHash(toks[1]);
		c.setXMLHash(toks[2]);
		return c;
	}
	
}
