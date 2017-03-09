package com.jgg.sdp.cics.parser;

import java_cup.runtime.Symbol;

public class CICSParm {

	private String parm;
	private String value = null;
	private int    type  = 0;
	
	public CICSParm(Symbol parm, Symbol value) {
	   
		this.parm = (String) parm.value;
		if (value != null) {
		   this.value = (String) value.value;
		   this.type = value.sym;
		}   
	}
	
	public String  getName()  { return parm;  }
	public String  getValue() { return value; }
	public int     getType()  { return type;  }
	
	// Parametro es PARM(valor), Opcion es PARM
	public boolean isParm()   { return value != null; }
	public boolean isOption() { return value == null; }
}
