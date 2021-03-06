package com.jgg.sdp.blocks.stmt;

import java.util.*;

import com.jgg.sdp.parser.symbols.SDPSymbol;

public class BlockCode {

	private SDPSymbol header;
	private int level = 0;
	protected String name;
	
	private ArrayList<Statement> stmts = new ArrayList<Statement>();
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	
	public BlockCode (String name) {
		this.name = name;
	}
	
	public BlockCode(SDPSymbol s) {
		header = s;
		name = (String) s.value;
	}

	public BlockCode(SDPSymbol s, SDPSymbol p) {
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

	public SDPSymbol getHeader() {
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
