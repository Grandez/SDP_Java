/**
 * Contiene una opcion de una sentencia y sus variables asociadas
 * Por ejemplo: PERFORM XX UNTIL xxx
 */

package com.jgg.sdp.blocks.stmt;

import java.util.*;

import com.jgg.sdp.blocks.reflect.*;
import com.jgg.sdp.blocks.symbols.*;

import java_cup.runtime.Symbol;

public class Option implements IReflect {
     private int id;
     private String name;
     private Symbol sym;
     
     private int begLine;
     private int endLine;
     private int begColumn;
     private int endColumn;
     
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
    	 first(s);
     }

     public Option(Symbol s, SymbolExt t) {
    	 first(s);
    	 last(t);
     }
     
     public Option(Symbol s, Symbol t) {
    	 first(s);
    	 last(t);
     }
/*     
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
*/     
     public Option addValue(SymbolExt v) {
    	 vars.add(v);
    	 return this;
     }
     public Option addValue(Symbol v) {
    	 vars.add(new SymbolExt(v));
    	 return this;
     }

     public void addValue(SymbolList l) {
    	 SymbolExt e = new SymbolExt(l.getSymbol());
//    	 StringBuilder sb = new StringBuilder();
//    	 for (Symbol s : l.getSymbols()) sb.append((String)((Symbol)s.value).value + " "); 
    	 vars.add(e);
     }

 	public Symbol getSymbol() {
		return sym;
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

//	public SymbolExt getAsSymbolExt() {
//		return sym;
//	}
	
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

	public String  getValue() {
		if (vars.size() == 0) return null;
		return vars.get(0).getName();
	}
	
	// Parametro es PARM(valor), Opcion es PARM
	public boolean isParm()   { return vars.size() != 0; }
	public boolean isOption() { return vars.size() == 0; }
	
    public Option addSymbol(SymbolExt s) {
    	vars.add(s);
    	return this;
    }
    
    public Option add(SymbolList list) {
    	for (Symbol s : list.getSymbols()) {
    		vars.add(new SymbolExt(s));
    	}
    	return this;
    }

    private void first(Symbol s) {
   	   this.id = s.sym;
   	   this.name = (String) s.value;
   	   sym = s;
   	   begLine = s.left;
   	   begColumn = s.right;
   	   endLine = s.left;
   	   endColumn = s.right;
    }
    
    private void last(Symbol s) {
    	last(new SymbolExt(s));
    }
    
    private void last(SymbolExt s) {
		if (s == null) return;
	    vars.add(s);
	    endLine = s.getEndLine();
	    endColumn = s.getEndColumn();
    }
    
    /***************************************************************/
	/***   INTERFAZ PARA LLAMADAS INTROSPECTION                  ***/
	/***************************************************************/

    public String toString() {
    	return name;
    }
    
    public String toValue() {
    	StringBuilder sb = new StringBuilder();
    	for (SymbolExt s : vars) {
    		sb.append(s.getValue());
    		sb.append(" ");
    	}
    	return sb.toString().trim();    	
    }

    public int getBegLine()   { return begLine;     }
    public int getEndLine()   { return endLine;     }
    public int getBegColumn() { return begColumn;   }
    public int getEndColumn() { return endColumn;   }    
    public int getLines()     { return endLine - begLine + 1; }

}
