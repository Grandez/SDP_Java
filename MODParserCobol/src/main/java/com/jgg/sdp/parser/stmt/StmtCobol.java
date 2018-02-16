package com.jgg.sdp.parser.stmt;

import com.jgg.sdp.blocks.stmt.Option;
import com.jgg.sdp.blocks.stmt.Statement;
import com.jgg.sdp.parser.symbols.*;

public class StmtCobol extends Statement<StmtCobol> {

	public StmtCobol(StmtCobol s) {
		super(s);
	}

	public StmtCobol(SDPSymbol s) {
		super(s);
	}
	
	public StmtCobol(SDPSymbol s, StmtCobol curr) {
		super(s);
		curr = this;
	}

	public StmtCobol(SDPSymbol s, int stmts) {
		super(s, stmts);
	}

	public StmtCobol(SDPSymbol s, SDPSymbol l)  {
		super(s, l);
	}
	
	// Caso WHEN / WHEN OTHER
	// Podria quedarse un WHEN huerfano
	public Option replaceOption(Option opt) {
		return options.replace(opt);
	}		

}
