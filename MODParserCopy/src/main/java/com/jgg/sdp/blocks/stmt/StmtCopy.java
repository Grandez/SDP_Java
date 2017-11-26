package com.jgg.sdp.blocks.stmt;

import java.util.ArrayList;

import com.jgg.sdp.blocks.symbols.*;

import java_cup.runtime.Symbol;

public class StmtCopy extends Statement<StmtCopy> {

	private boolean ignored = false;
	
	public StmtCopy(Symbol s) {
		super(s);
	}
	
	public String getCopyName() {
		return getVerbName();
	}
	
	public ArrayList<String> getReplacingTokens() {
		OptionGroup opt = getOption("REPLACING");
		if (opt == null) return null;
		
		ArrayList<String> toks = new ArrayList<String>();
		
		for (SymbolExt s : opt.getOption().getVars()) {
			toks.add(s.getName());
		}
		return toks;
	}
	
	public void setIgnored()   { ignored = true; }
	public boolean isIgnored() { return ignored; }
}
