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

import java_cup.runtime.Symbol;

import static com.jgg.sdp.parser.lang.ZCDSym.*;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.items.*;

public class ZCDCode extends ZCZCode{

    public ZCDCode(Module module) {
    	super(module);
	}

    
    public Variable getVariable(Symbol s) {
    	return null;
    	/*JGG
    	String name = (String) s.value;
    	Variable var = module.getVariable(name);
    	if (var == null) module.setVarMissing();
        return var;
        */	
    }

    public Variable setVarType(Variable var, Symbol type) {
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
    
    public Symbol calculateSizeLen(Variable var, Symbol sym) {
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

    public Symbol calculateSizeFormat(Variable var, Symbol sym) {
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

    public Symbol calculateSize(Variable var, Symbol sym) {
    	String szSize = (String) sym.value;
    	var.addSize(szSize.length() - 1);
    	return sym;
    }

    public Variable addSize(Variable var, int size) {
    	var.addSize(size);
    	return var;
    }

    public Symbol addSize(Variable var, Symbol sym) {
    	String szSize = (String) sym.value;
    	var.addSize(Integer.parseInt(szSize));
    	return sym;
    }
    
	public void setBounds(Variable var, Symbol from, Symbol to) {
		Integer min = Integer.parseInt((String) from.value);
        Integer max = Integer.parseInt((String) to.value);
        if (max < min) max = min;
        var.setBounds(min, max);
	}
    
	
	public Variable createVar(Symbol level, Symbol name) {
		Variable var = new Variable((String) level.value, (String) name.value);
		var.setLine(level.left);
		var.setColumn(level.right);
		var.setSection(info.getSection());
		return var;
	}

}
