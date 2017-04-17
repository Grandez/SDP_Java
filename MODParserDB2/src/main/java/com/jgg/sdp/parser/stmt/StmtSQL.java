package com.jgg.sdp.parser.stmt;

import java.util.*;

import com.jgg.sdp.parser.base.stmt.Statement;

import java_cup.runtime.Symbol;

public class StmtSQL extends Statement<StmtSQL> {

	private HashMap<String, SQLTable> tables    = new HashMap<String, SQLTable>();

	public StmtSQL() {
		super();
	}
	
	public StmtSQL(Symbol s) {
		super(s);
	}
	
	public boolean isInclude() {
		return getVerbName().compareToIgnoreCase("INCLUDE") == 0;
	}
	
	public void addTable(SQLTable table) {
		String name = table.getName();
		SQLTable t = tables.get(name); 
		if ( t == null) {
		    tables.put(name, table);
		} else {
			t.incReferences();
		}
	}
	
	public void addFunction(String name) {
		
	}
}
