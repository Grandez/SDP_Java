/**
 * Lista de variables
 */
package com.jgg.sdp.parser.work;

import java.util.ArrayList;

import java_cup.runtime.*;

public class SymbolList {
	private ArrayList<Symbol> lista = new ArrayList<Symbol>();

    public SymbolList (Symbol t) {
        lista.add(t);
    }

	public SymbolList add(Symbol v) {
		lista.add(v);
		return this;
	}
	
    public ArrayList<Symbol> getVarList() {
        return lista;
    }

    public int getNumSymbols() {
    	return lista.size();
    }
    
    public Symbol getSymbol() {
    	return getSymbol(0);
    }
    
    public Symbol getSymbol(int index) {
    	return lista.get(index);
    }
}
