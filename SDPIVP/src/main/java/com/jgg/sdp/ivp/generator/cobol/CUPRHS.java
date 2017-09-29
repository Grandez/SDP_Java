package com.jgg.sdp.ivp.generator.cobol;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Implementa una produccion para un determinado no terminal
 */
import com.jgg.sdp.adt.SDPList;

public class CUPRHS {

	private String name = "";
	private String alias = "";
	// Tiene no terminales?
	private int nonTerminals = 0;
	
	private SDPList<CUPComponent> rhs = new SDPList<CUPComponent>(); 
	
	public void add(CUPComponent c) { 
		rhs.add(c);
		if (rhs.size() == 1) {
			name = c.getName();
			alias = name;
		}
		if (c instanceof CUPNonTerminal) nonTerminals++;
	}

	public void replace(CUPComponent[] c) {
		nonTerminals = 0;
		rhs = new SDPList<CUPComponent>();
		for (int idx = 0; idx < c.length; idx++) rhs.add(c[idx]);
	}
	
	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}
	
	public void setName(String name) {
		this.name = name;
		this.alias = name;
	}

	public boolean isProduction() {
		return (nonTerminals > 0);
	}
	
	public SDPList<CUPComponent> getRhs() {
		return rhs;
	}

	public CUPComponent[] getProductionAsArray() {
		Object[] o = rhs.asObjectArray();
		CUPComponent[] c = new CUPComponent[o.length];
		for (int idx = 0; idx < o.length; idx++) c[idx] = (CUPComponent) o[idx];
		return c;
	}
	
	public boolean contains (String nonterminal) {
         for (CUPComponent c : rhs) {
        	 if (nonterminal.compareTo(c.getName()) == 0) return true;
         }
         return false;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (CUPComponent c : rhs) {
			if (sb.length() > 0) sb.append(" ");
			sb.append(c.getName());
		}
		return sb.toString();
	}
	
	public void remove(HashSet<String> items) {
		HashSet<CUPComponent> r = new HashSet<CUPComponent>();  
		for (CUPComponent c : rhs) {
			if (items.contains(c.getName())) r.add(c); 
		}
		Iterator<CUPComponent> it = r.iterator();
		while (it.hasNext()) rhs.remove(it.next());
	}
}
