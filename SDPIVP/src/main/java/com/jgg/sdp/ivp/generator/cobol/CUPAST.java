package com.jgg.sdp.ivp.generator.cobol;

import java.util.*;

public class CUPAST {

	public HashMap<String, CUPTerminal>    terminals    = new HashMap<String, CUPTerminal>();
    public HashMap<String, CUPNonTerminal> nonTerminals = new HashMap<String, CUPNonTerminal>();
    public HashMap<String, CUPRHSTerminal> rhsTerminals = new HashMap<String, CUPRHSTerminal>();
    public HashMap<String, String>         dictionary   = new HashMap<String, String>();
    
    public List<CUPNonTerminal> getNonTerminals() {
    	return new ArrayList<CUPNonTerminal>(nonTerminals.values());
    }
    
    public void removeNonTerminal(CUPNonTerminal nt) {
    	nonTerminals.remove(nt.getName());
    }
}
