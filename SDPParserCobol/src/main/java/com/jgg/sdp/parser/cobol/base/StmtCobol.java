package com.jgg.sdp.parser.cobol.base;

import com.jgg.sdp.parser.base.stmt.Statement;
import com.jgg.sdp.parser.base.stmt.Tokens;

import java_cup.runtime.Symbol;

public class StmtCobol extends Statement{

	public StmtCobol(StmtCobol s) {
		super(s);
	}

	public StmtCobol(Symbol s) {
		super(s);
	}

	public StmtCobol(Symbol s, int stmts) {
		super(s, stmts);
	}
	
	public StmtCobol addTokens(Tokens tokens) {
		if (tokens == null) return this;
		return (StmtCobol) addSymbols(tokens.getTokens());
	}
	
	public StmtCobol addSymbol(Symbol s) {
		tokens.add(s);
		return this;
	}


	
}
