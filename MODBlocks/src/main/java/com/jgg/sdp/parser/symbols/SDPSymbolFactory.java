package com.jgg.sdp.parser.symbols;

import com.jgg.sdp.blocks.stmt.Option;

import java_cup.runtime.*;

public class SDPSymbolFactory implements SymbolFactory{


	public Symbol newSymbol(String value, int id) {
		return new Symbol(id, -1, -1, new SDPSymbol(id, -1, -1, value));
	}

	public Symbol newSymbol(int id, String value) {
		return new Symbol(id, -1, -1, new SDPSymbol(id, -1, -1, value));
	}

	public Symbol newSymbol(int id, int line, int column, String value) {
		return new Symbol(id, line, column, new SDPSymbol(id, line, column, value));
	}
	public Symbol newSymbol(int id, int line, int column, Object value) {
		return new Symbol(id, line, column, value);
	}

	public SDPSymbol newSDPSymbol(SDPSymbol s) {
		SDPSymbol n = new SDPSymbol(s.getId(), s.getLine(), s.getColumn(), s.getValue());
 		return n; 
	}

	public Symbol startSymbol(String arg0, int arg1, int arg2) {
		Symbol s = new Symbol(arg1, arg2);
		s.value =  new SDPSymbol(arg1, arg0);
		return s;
	}
	
	public Symbol newSymbol(String arg0, int arg1, Object arg2) {
 		throw new UnsupportedOperationException(); 
	}

	public Symbol newSymbol(String arg0, int arg1, Symbol arg2, Symbol arg3) {
 		throw new UnsupportedOperationException(); 
	}

	public Symbol newSymbol(String arg0, int arg1, Symbol arg2, Object arg3) {
 		throw new UnsupportedOperationException(); 
	}

	public Symbol newSymbol(String arg0, int arg1, Symbol arg2, Symbol arg3, Object arg4) {
 		throw new UnsupportedOperationException(); 
	}
	
	public Option option(SDPSymbol o, SDPSymbol w) {
		return new Option(o, w);
	}
	public Option option(SDPSymbol s, Option o) {
		return new Option(s, o);
	}
	public Option option(SDPSymbol s) {
		return new Option(s);
	}

}
