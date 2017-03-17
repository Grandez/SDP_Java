package com.jgg.sdp.parser.db2.stmt;

import java.util.*;

import java_cup.runtime.Symbol;

import com.jgg.sdp.parser.base.Statement;

public class StmtSQL extends Statement {

	private HashMap<String, SQLTable> tables    = new HashMap<String, SQLTable>();
	private HashMap<String, Integer> functions  = new HashMap<String, Integer>();
	
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
