/**
 * Lista de variables
 */
package com.jgg.sdp.blocks.symbols;

import java.util.ArrayList;

import java_cup.runtime.*;

public class SymbolList {
	private ArrayList<SymbolExt> lista = new ArrayList<SymbolExt>();

    public SymbolList (SymbolExt t) {
        lista.add(t);
    }
    public SymbolList (Symbol t) {
        lista.add(new SymbolExt(t));
    }

	public SymbolList add(SymbolExt v) {
		lista.add(v);
		return this;
	}
	public SymbolList add(Symbol v) {
		lista.add(new SymbolExt(v));
		return this;
	}
	
    public ArrayList<SymbolExt> getSymbols() {
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
