package com.jgg.sdp.parser.db2.base;

import com.jgg.sdp.parser.base.Statement;

import java_cup.runtime.Symbol;

public class StmtSQL extends Statement {

	public StmtSQL() {
		super();
	}
	
	public StmtSQL(Symbol s) {
		super(s);
	}
	
	public boolean isInclude() {
		return getVerbName().compareToIgnoreCase("INCLUDE") == 0;
	}
}
