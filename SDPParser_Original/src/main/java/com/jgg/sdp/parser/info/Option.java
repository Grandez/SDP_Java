/**
 * Contiene una opcion de una sentencia y sus variables asociadas
 * Por ejemplo: PERFORM XX UNTIL xxx
 */

package com.jgg.sdp.parser.info;

import java.util.*;

import com.jgg.sdp.parser.work.SymbolExt;
import com.jgg.sdp.parser.work.SymbolList;

import java_cup.runtime.Symbol;

public class Option {
     private int id;
     private String name;
     private SymbolExt sym;
     private ArrayList<SymbolExt> vars = new ArrayList<SymbolExt>();

     public Option() {
    	 this.id = -1;
    	 this.name = "";
     }
     
     public Option(int id, String name) {
    	 this.id = id;
    	 this.name = name;
     }
     
     public Option(Symbol s) {
    	 this.id = s.sym;
    	 this.name = (String) s.value;
    	 sym = new SymbolExt(s);
//    	 vars.add(new SymbolExt(s));
     }

     public Option(Symbol s, Symbol t) {
    	 this.id = s.sym;
    	 this.name = (String) s.value;
//    	 vars.add(new SymbolExt(s));
    	 vars.add(new SymbolExt(t));
     }
     
     public Option(Symbol s, Tokens t) {
    	 this.id = s.sym;
    	 this.name = (String) s.value;
//    	 vars.add(new SymbolExt(s));
    	 for (Symbol v : t.getTokens()) {
            vars.add(new SymbolExt(v));
    	 }   
     }

     public Option(Symbol s, Statement stmt) {
    	 this.id = s.sym;
    	 this.name = (String) s.value;
//    	 vars.add(new SymbolExt(s));
     }
     
     // Para evitar el cast a String
     public Option(int id, Object name) {
    	 this.id = id;
    	 this.name = (String) name;
     }
     
     public Option(int id, String name, SymbolExt var) {
    	 this.id = id;
    	 this.name = name;
    	 this.vars.add(var);
     }

     public Option(int id, String name, Symbol var) {
    	 this.id = id;
    	 this.name = name;
    	 this.vars.add(new SymbolExt(var));
     }
     
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SymbolExt getAsSymbolExt() {
		return sym;
	}
	
	public SymbolExt getVar(int pos) {
		if (vars.size() == 0) return null;
		return vars.get(pos);
	}
	
	public ArrayList<SymbolExt> getVars() {
		return vars;
	}

	public void setVars(ArrayList<SymbolExt> vars) {
		this.vars = vars;
	}
     
    public Option addSymbol(SymbolExt s) {
    	vars.add(s);
    	return this;
    }
    
    public Option add(SymbolList list) {
    	for (Symbol s : list.getVarList()) {
    		vars.add(new SymbolExt(s));
    	}
    	return this;
    }
}
