package com.jgg.sdp.parser.base;

import com.jgg.sdp.parser.base.stmt.*;
import com.jgg.sdp.parser.base.symbol.*;

import java_cup.runtime.Symbol;

public class StmtCobol extends Statement<StmtCobol>{

	public StmtCobol(StmtCobol s) {
		super(s);
	}

	public StmtCobol(Symbol s) {
		super(s);
	}
	
	public StmtCobol(Symbol s, StmtCobol curr) {
		super(s);
		curr = this;
	}

	public StmtCobol(Symbol s, int stmts) {
		super(s, stmts);
	}

	public StmtCobol(Symbol s, SymbolList l)  {
		super(s,l);
	}
	
	// Caso WHEN / WHEN OTHER
	// Podria quedarse un WHEN huerfano
	public Option replaceOption(Option opt) {
		Option last = lstOptions.get(lstOptions.size() - 1);
	    opt.addSymbol(last.getAsSymbolExt());
	    lstOptions.remove(lstOptions.size() - 1);
	    return addOption(opt);
	}		

}
