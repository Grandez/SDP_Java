package com.jgg.sdp.ivp.generator.cobol;

import java.util.*;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

public class CUPCode {

	/**
	 * No terminales a ignorar
	 * Sabemos que el parser es correcto, asi que cuando haya nulos
	 * es por que ha sido ignorado
	 */

	private final String[] IGN_WORDS = 
			new String[] {  "function"
					      , "padres_opt"
					      , "indices_opt"
					      , "indices"
					      , "indice"
					 
			};

	private final String[] IGN_MASK = 
			new String[] {  "^optword*"
					 
			};
	
	private final Set<String> IGNORED = new HashSet<>(Arrays.asList(IGN_WORDS));
	private ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();
	
	private CUPNonTerminal lhs = null;
	private CUPRHS         rhs = null;
    private CUPAST         ast = new CUPAST();
    	
	public CUPCode() {
		CUPTerminal t = new CUPTerminal();
		t.setId(-1);
		t.setName("empty");
		ast.terminals.put(t.getName(), t);

	}
	
	public boolean isIgnored(String tok) {
		if (IGNORED.contains(tok)) return true;
		for (int idx = 0; idx < IGN_MASK.length; idx++) {
			if (tok.matches(IGN_MASK[idx])) return true;
		}
		return false;
	}
	
    public CUPAST getAST()  {
    	return ast;
    }
    
	public void addTerminal(Symbol s) {
		CUPTerminal t = new CUPTerminal();
		t.setId(s.sym);
		t.setName((String) s.value);
		ast.terminals.put(t.getName(), t);
	}
	
	public void addNonTerminal(Symbol s) {
		if (isIgnored((String) s.value)) return;
		
		CUPNonTerminal t = new CUPNonTerminal();
		t.setId(s.sym);
		t.setName((String) s.value);
		ast.nonTerminals.put(t.getName(), t);
	}

	public void setCurrentLHS(Symbol id) {
	     lhs = ast.nonTerminals.get((String) id.value);
	}

	public void setCurrentRHS() {
	     rhs = new CUPRHS();
	}
	
	public void addComponent(Symbol s) {
		String value = (s.value instanceof Symbol) ? (String) ((Symbol) s.value).value 
				                                   : (String) s.value; 
		CUPComponent c = ast.terminals.get(value);
		if (c == null) {
			c = ast.nonTerminals.get(value);
		}
		if (c != null) rhs.add(c);
	}
	
	public void addRHS() {
		if (lhs != null) lhs.addProduction(rhs);
	}
}
