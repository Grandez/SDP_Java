package com.jgg.sdp.parser.info;

import java.util.ArrayList;

import com.jgg.sdp.parser.work.SymbolExt;

import java_cup.runtime.Symbol;

public class StmtCopy extends Statement {

	private boolean ignored = false;
	
	public StmtCopy(Symbol s) {
		super(s);
	}
	
	public String getCopyName() {
		return getRValue(0).getName();
	}
	
	public ArrayList<String> getReplacingTokens() {
		Option opt = getOptionByName("REPLACING");
		if (opt == null) return null;
		
		ArrayList<String> toks = new ArrayList<String>();
		
		for (SymbolExt s : opt.getVars()) {
			toks.add(s.getName());
		}
		return toks;
	}
	
	public void setIgnored()   { ignored = true; }
	public boolean isIgnored() { return ignored; }
}
