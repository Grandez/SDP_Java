/**
 * Contiene todas las acciones semanticas independientemente del 
 * dialecto COBOL
 * De esta forma se separa el proceso de analisis de sus acciones
 * reutilizando el codigo
 *  
 * Casos especiales en algun dialecto deberian extender esta clase
 * y sobreescribir las acciones correspondientes
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.code;

import static com.jgg.sdp.parser.lang.ZCDSym.*;

import com.jgg.sdp.blocks.stmt.Statement;
import com.jgg.sdp.common.ctes.CDG;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.parser.symbols.SDPSymbol;
import com.jgg.sdp.rules.components.RulesData;

public class ZCDCode extends ZCZCode{

	private RulesData rules = new RulesData();
	
    public ZCDCode(Module module) {
    	super(module);
	}

    public void setDivision(int div, int line) {
    	info.addDivision(div, line);
    }
    public void setSection(int sect, int line) {
    	info.addSection(sect, line);
    }
    
    public Variable getVariable(SDPSymbol s) {
    	return null;
    	/*JGG
    	String name = (String) s.value;
    	Variable var = module.getVariable(name);
    	if (var == null) module.setVarMissing();
        return var;
        */	
    }

    public Variable setVarType(Variable var, SDPSymbol type) {
    	switch (type.sym) {
    	   case COMP1:   var.setType(VAR.COMP1);   break;
    	   case COMP2:   var.setType(VAR.COMP2);   break;
    	   case COMP3:   var.setType(VAR.COMP3);   break;
    	   case COMP4:   var.setType(VAR.COMP4);   break;
    	   case COMP5:   var.setType(VAR.COMP5);   break;
    	   case COMP6:   var.setType(VAR.COMP6);   break;    	   
    	   case BINARY:  var.setType(VAR.BINARY);  break;
           case POINTER: var.setType(VAR.POINTER); break;        	   
    	}
    	return var;
    }
    
    public SDPSymbol calculateSizeLen(Variable var, SDPSymbol sym) {
    	String szSize = (String) sym.value;
    	int pos = szSize.indexOf('(');

    	int size = 0;
    	char car = szSize.charAt(++pos);
    	while (car != ')') {
    		switch(car) {
    		   case '0': size *= 10; break;
    		   case '1': size = (size * 10) + 1; break;
    		   case '2': size = (size * 10) + 2; break;
    		   case '3': size = (size * 10) + 3; break;
    		   case '4': size = (size * 10) + 4; break;
    		   case '5': size = (size * 10) + 5; break;
    		   case '6': size = (size * 10) + 6; break;
    		   case '7': size = (size * 10) + 7; break;
    		   case '8': size = (size * 10) + 8; break;
    		   case '9': size = (size * 10) + 9; break;
    		   case 'm':
    		   case 'M': size *= (1024 * 1024); break;	   
    		   case 'k':
    		   case 'K': size *= 1024; break;	   
    		}
    		car = szSize.charAt(++pos);
    	}
    	var.addSize(size);
    	return sym;
    }

    public SDPSymbol calculateSizeFormat(Variable var, SDPSymbol sym) {
    	String szSize = (String) sym.value;
    	char c;
    	int size = 0;
    	for (int idx = 0; idx < szSize.length(); idx++) {
    		c = szSize.charAt(idx);
    		if (c != '.' && c != ',') size++;
    	}
    	var.addSize(size);
    	return sym;
    }

    public SDPSymbol calculateSize(Variable var, SDPSymbol sym) {
    	String szSize = (String) sym.value;
    	var.addSize(szSize.length() - 1);
    	return sym;
    }

    public Variable addSize(Variable var, SDPSymbol size) {
    	var.addSize(Integer.parseInt(size.value));
    	return var;
    }

    public Variable addSize(Variable var, int size) {
    	var.addSize(size);
    	return var;
    }
    
	public void setBounds(Variable var, SDPSymbol from, SDPSymbol to) {
		Integer min = Integer.parseInt((String) from.value);
        Integer max = Integer.parseInt((String) to.value);
        if (max < min) max = min;
        var.setBounds(min, max);
	}
    
	
	public Variable createVar(SDPSymbol level, SDPSymbol name) {
		Variable var = new Variable((String) level.value, (String) name.value);
		var.setLine(level.line);
		var.setColumn(level.column);
		var.setSection(info.getSection());
		return var;
	}

   public SDPSymbol removePoint(SDPSymbol s) {
	   String aux = s.value;
	   if (aux.endsWith(".")) {
	       aux = aux.substring(0, aux.length() - 1);
	       aux = aux.trim();
	   }
	   s.value = aux;
	   return s;
   }

	private void processIdentification(Statement stmt) {
		info.addDivision(CDG.SECT_ID, stmt.getVerb().line);
		rules.checkIdentification(stmt);
	}
}
