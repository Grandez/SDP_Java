package com.jgg.sdp.parser.stmt;

import java.util.ArrayList;

import com.jgg.sdp.blocks.stmt.Option;
import com.jgg.sdp.blocks.stmt.Statement;
import com.jgg.sdp.parser.symbols.*;

public class StmtCopy extends Statement<StmtCopy> {

	private boolean ignored = false;
	
	public StmtCopy(SDPSymbol s) {
		super(s);
	}
	
	public String getCopyName() {
		return getVerbName();
	}
	
	public ArrayList<String> getReplacingTokens() {
		Option opt = getOptionByName("REPLACING");
		if (opt == null) return null;
		
		ArrayList<String> toks = new ArrayList<String>();
		
		for (SDPSymbol s : opt.getParms()) {
			toks.addAll(s.getValues());
		}
		return toks;
	}
	
	public void setIgnored()   { ignored = true; }
	public boolean isIgnored() { return ignored; }
}
