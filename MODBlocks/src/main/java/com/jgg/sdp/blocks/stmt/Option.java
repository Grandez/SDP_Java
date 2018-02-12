/**
 * Contiene una opcion de una sentencia y sus variables asociadas
 * Por ejemplo: PERFORM XX UNTIL xxx
 */

package com.jgg.sdp.blocks.stmt;

import java.util.*;

import com.jgg.sdp.blocks.reflect.*;
import com.jgg.sdp.parser.symbols.*;

public class Option implements IReflect {
     private int id;
     private String name;
     private SDPSymbol sym;
     
     private int begLine;
     private int endLine;
     private int begColumn;
     private int endColumn;
     
 	// Indica si el texto va entre parentesis
 	public boolean function = false;

     private ArrayList<SDPSymbol> parms = new ArrayList<SDPSymbol>();
     private ArrayList<Option>    opts  = new ArrayList<Option>();
     
     public Option() {
    	 this.id = -1;
    	 this.name = "";
     }
     
     public Option(int id, String name) {
    	 this.id = id;
    	 this.name = name;
     }
     
     public Option(SDPSymbol s) {
    	 first(s);
     }

     public Option(SDPSymbol s, SDPSymbol t) {
    	 first(s);
    	 if (t != null) last(t);
     }
     public Option(SDPSymbol s, Option o) {
    	 first(s);
    	 if (o != null) opts.add(o);
     }
     
     public Option add(SDPSymbol v) {
    	 parms.add(v);
    	 return this;
     }
     public Option add(Option o) {
    	 opts.add(o);
    	 return this;
     }

 	public SDPSymbol getSymbol() {
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

	public Option setFunction() {
		function = true;
		return this;
	}
	
	public Option addParm(SDPSymbol s) {
		parms.add(s);
		return this;
	}
	public SDPSymbol getParm(int pos) {
		if (parms.size() == 0) return null;
		return parms.get(pos);
	}
	
	public ArrayList<SDPSymbol> getParms() {
		return parms;
	}

	public void setParms(ArrayList<SDPSymbol> parms) {
		this.parms = parms;
	}

	public String  getValue() {
		if (parms.size() == 0) return null;
		return parms.get(0).getValue();
	}
	public int  getValueType() {
		if (parms.size() == 0) return 0;
		return parms.get(0).getId();
	}
	
	// Parametro es PARM(valor), Opcion es PARM
	public boolean isParm()   { return parms.size() != 0; }
	public boolean isOption() { return parms.size() == 0; }
	
    public Option addSymbol(SDPSymbol s) {
    	parms.add(s);
    	return this;
    }
    
//    public Option add(SDPSymbol list) {
//    	for (SDPSymbol s : list.getSymbols()) {
//    		vars.add(new SDPSymbol(s));
//    	}
//    	return this;
//    }

    private void first(SDPSymbol s) {
   	   this.id = s.sym;
   	   this.name = (String) s.value;
   	   sym = s;
   	   begLine = s.getLine();
   	   begColumn = s.getColumn();
   	   endLine = s.getLine();
   	   endColumn = s.getColumn() + s.getValue().length();
    }
        
    private void last(SDPSymbol s) {
		if (s == null) return;
	    parms.add(s);
	    endLine = s.getLine();
	    endColumn = s.getColumn() + s.getValue().length();
    }
    
    /***************************************************************/
	/***   INTERFAZ PARA LLAMADAS INTROSPECTION                  ***/
	/***************************************************************/

    public String toString() {
    	return name;
    }
    
    public String toValue() {
    	StringBuilder sb = new StringBuilder();
    	for (SDPSymbol s : parms) {
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
