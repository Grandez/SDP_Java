/**
 * Implementa una lista de Tokens <br> 
 * Estos Tokens siempre van asociados a una Statement
 * <br>
 *
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.blocks.stmt;

import java.util.ArrayList;

import com.jgg.sdp.parser.symbols.SDPSymbol;

public class Tokens {

	private ArrayList<SDPSymbol> toks = new ArrayList<SDPSymbol>();
	
	public Tokens() {
		
	}
	
	public Tokens(SDPSymbol s) {
		toks.add(s);
	}

	public Tokens(Tokens t) {
		for (SDPSymbol s : t.getTokens()) {
			toks.add(s);
		}
	}

	public Tokens(SDPSymbol s, Tokens t) {
		this(s);
		for (SDPSymbol ss : t.getTokens()) {
			toks.add(ss);
		}
	}

	public Tokens add(SDPSymbol s) {
		toks.add(s);
		return this;
	}
	
	public Tokens add(Tokens lista) {
		for (SDPSymbol s : lista.getTokens()) {
			toks.add(s);
		}
		return this;
	}
	
	public ArrayList<SDPSymbol> getTokens() {
		return toks;
	}
	 
}
