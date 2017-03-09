/**
 * Implementa una lista de Tokens <br> 
 * Estos Tokens siempre van asociados a una Statement
 * <br>
 *
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.info;

import java.util.ArrayList;

import java_cup.runtime.Symbol;

public class Tokens {

	private ArrayList<Symbol> toks = new ArrayList<Symbol>();
	
	public Tokens() {
		
	}
	
	public Tokens(Symbol s) {
		toks.add(s);
	}

	public Tokens(Tokens t) {
		for (Symbol s : t.getTokens()) {
			toks.add(s);
		}
	}

	public Tokens(Symbol s, Tokens t) {
		this(s);
		for (Symbol ss : t.getTokens()) {
			toks.add(ss);
		}
	}

	public Tokens add(Symbol s) {
		toks.add(s);
		return this;
	}
	
	public Tokens add(Tokens lista) {
		for (Symbol s : lista.getTokens()) {
			toks.add(s);
		}
		return this;
	}
	
	public ArrayList<Symbol> getTokens() {
		return toks;
	}
	 
}
