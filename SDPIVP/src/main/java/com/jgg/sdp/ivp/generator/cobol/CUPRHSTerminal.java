package com.jgg.sdp.ivp.generator.cobol;

import com.jgg.sdp.adt.*;

public class CUPRHSTerminal extends CUPComponent {

	// Conjunto de producciones
	private SDPList<CUPRHS> productions = new SDPList<CUPRHS>();

	public CUPRHSTerminal(CUPNonTerminal nt) {
		super(RHS_TERMINAL);

		productions = nt.getProductions();
		name = "rhs" + nt.getName();
		value = nt.getValue();		
	    id = nt.getId();
	}
		

	public SDPList<CUPRHS> getProductions() {
		return this.productions;
	}	
}
