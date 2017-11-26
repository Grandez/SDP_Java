package com.jgg.sdp.ivp.generator.cobol;

import com.jgg.sdp.adt.*;

public class CUPRHSTerminal extends CUPComponent {

	// Conjunto de producciones
	private ADTList<CUPRHS> productions = new ADTList<CUPRHS>();

	public CUPRHSTerminal(CUPNonTerminal nt) {
		super(RHS_TERMINAL);

		productions = nt.getProductions();
		name = "rhs" + nt.getName();
		value = nt.getValue();		
	    id = nt.getId();
	}
		

	public ADTList<CUPRHS> getProductions() {
		return this.productions;
	}	
}
