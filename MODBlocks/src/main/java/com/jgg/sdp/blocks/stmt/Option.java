/**
 * Contiene una opcion de una sentencia y sus variables asociadas
 * Por ejemplo: PERFORM XX UNTIL xxx
 */

package com.jgg.sdp.blocks.stmt;

import java.util.*;

import com.jgg.sdp.blocks.reflect.*;
import com.jgg.sdp.parser.symbols.*;

public class Option extends Reflect {
     private int id;
     private String name;
     private SDPSymbol sym;
     
 	// Indica si el texto va entre parentesis
 	public boolean function = false;

     private ArrayList<SDPSymbol> lstParms   = new ArrayList<SDPSymbol>();
     private ArrayList<Option>    lstOptions = new ArrayList<Option>();
     
     public Option() {
    	 super();
    	 this.id = -1;
    	 this.name = "";
     }
     
     public Option(int id, String name) {
    	 super();
    	 this.id = id;
    	 this.name = name;
     }
     
     public Option(SDPSymbol s) {
    	 super();
    	 first(s);
    	 setLines(true, (Reflect) s);
     }

     public Option(SDPSymbol s, SDPSymbol t) {
    	 first(s);
    	 if (t != null) last(t);
    	 setLines(s, t);
     }
     public Option(SDPSymbol s, Option o) {
    	 first(s);
    	 if (o != null) lstOptions.add(o);
    	 setLines(s, o);
     }
     
     public Option add(SDPSymbol s) {
    	 lstParms.add(s);
    	 setLines(false, s);
    	 return this;
     }
     public Option add(Option o) {
    	 lstOptions.add(o);
    	 setLines(false, o);
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
		lstParms.add(s);
		return this;
	}
	public SDPSymbol getParm(int pos) {
		if (lstParms.size() == 0) return null;
		return lstParms.get(pos);
	}
	
	public ArrayList<SDPSymbol> getParms() {
		return lstParms;
	}

	public void setParms(ArrayList<SDPSymbol> parms) {
		this.lstParms = parms;
	}

	public String  getValue() {
		if (lstParms.size() == 0) return null;
		return lstParms.get(0).getValue();
	}
	public int  getValueType() {
		if (lstParms.size() == 0) return 0;
		return lstParms.get(0).getId();
	}
	
	// Parametro es PARM(valor), Opcion es PARM
	public boolean isParm()   { return lstParms.size() != 0; }
	public boolean isOption() { return lstParms.size() == 0; }
	
    public Option addSymbol(SDPSymbol s) {
    	lstParms.add(s);
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
	    lstParms.add(s);
	    endLine = s.getLine();
	    endColumn = s.getColumn() + s.getValue().length();
    }

    /***************************************************************/
	/***   INTERFAZ PARA LLAMADAS INTROSPECTION                  ***/
	/***************************************************************/

	public List<Option> getOptionList() { return (List<Option>) lstOptions; }
	
    public String toString() {
    	return name;
    }
    
    public String toValue() {
    	StringBuilder sb = new StringBuilder();
    	for (SDPSymbol s : lstParms) {
    		sb.append(s.getValue());
    		sb.append(" ");
    	}
    	return sb.toString().trim();    	
    }

}
