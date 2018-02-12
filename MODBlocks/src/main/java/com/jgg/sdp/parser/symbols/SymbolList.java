/**
 * Lista de variables
 */
package com.jgg.sdp.parser.symbols;

import java.util.ArrayList;

import java_cup.runtime.*;

public class SymbolList {
	private ArrayList<SymbolExt> lista = new ArrayList<SymbolExt>();

    public SymbolList () {
    }
	
    public SymbolList (SymbolExt t) {
        lista.add(t);
    }
    public SymbolList (Symbol t) {
        lista.add(new SymbolExt(t));
    }

    public SymbolList (SymbolList t) {
    	for (SymbolExt s : t.getSymbols()) lista.add(s);
    }
    
	public SymbolList add(Object v) {
		if (v instanceof SymbolExt) lista.add((SymbolExt) v);
		if (v instanceof Symbol) lista.add(new SymbolExt((Symbol) v));
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
