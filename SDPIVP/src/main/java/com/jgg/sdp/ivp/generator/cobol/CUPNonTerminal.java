package com.jgg.sdp.ivp.generator.cobol;

import com.jgg.sdp.adt.*;

public class CUPNonTerminal extends CUPComponent {
	
	private CUPRHSTerminal rhs = null;
	
	// Contiene no terminales?
	protected int numProductions = 0;
	protected boolean reduced = false;
	
	// Conjunto de producciones
	protected ADTList<CUPRHS> productions = new ADTList<CUPRHS>();

	public CUPNonTerminal() {
		super(2);
		terminal = false;
	}

	public CUPNonTerminal(int type) {
		super(type);
		terminal = false;
	}
	
	public ADTList<CUPRHS> getProductions() {
		return productions;
	}

	/**
	 * Inserta las produciones no recursivas
	 * @param rhs
	 */
	public void addProduction(CUPRHS rhs) {
		if (!rhs.contains(this.getName())) {
		    productions.add(rhs);
		    if (rhs.isProduction()) numProductions++;
		}
	}
	
	public boolean hasProductions() {
		return (numProductions > 0);
	}

	public boolean isReduced() {
		return reduced;
	}
	
	public void setRHS(CUPRHSTerminal rhs) {
		this.rhs = rhs;
	}
	
	public CUPRHSTerminal getCUPRHSTerminal() {
		return rhs;
	}
	
	public boolean isRHSTerminal() {
		return (rhs != null);
	}
	
	public void setReduced(boolean reduced) {
		this.reduced = reduced;
	}
}
