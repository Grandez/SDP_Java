package com.jgg.sdp.parser.blocks;

import java.util.*;

import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.base.stmt.Statement;

import java_cup.runtime.Symbol;

public class BlockCode {

	private Symbol header;
	private int level = 0;
	protected String name;
	
	private ArrayList<Statement> stmts = new ArrayList<Statement>();
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	
	public BlockCode (String name) {
		this.name = name;
	}
	
	public BlockCode(Symbol s) {
		header = s;
		name = (String) s.value;
	}

	public BlockCode(Symbol s, Symbol p) {
        this(p);
    }
	
	public BlockCode addStatement(Statement stmt) {
		if (stmt == null) return this;
		stmts.add(stmt);
		if (!map.containsKey(stmt.getVerbName())) {
			map.put(stmt.getVerbName(), stmts.size() - 1);
		}
		return this;
	}

	public Symbol getHeader() {
		return header;
	}
	
	public String getHeaderName() {
		return (String) header.value;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Statement getStatement(String verb) {
		Integer pos = map.get(verb);
		return (pos == null) ? null : stmts.get(pos);
	}
	
 	public ArrayList<Statement> getStatements() {
		return stmts;
	}
	
}
