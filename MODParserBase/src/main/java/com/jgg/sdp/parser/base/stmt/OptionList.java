package com.jgg.sdp.parser.base.stmt;

import java.util.*;

import com.jgg.sdp.parser.base.symbol.SymbolExt;

public class OptionList {

	private HashMap<Integer, Option> options = new HashMap<Integer, Option>();
	
	public OptionList() {
		
	}
	public OptionList(Option opt) {
		this.options.put(opt.getId(), opt);
	}
	
	public OptionList add(Option opt) {
		options.put(opt.getId(), opt);
		return this;
	}
	
	public Option get(int id) {
		return options.get(id);
	}
	
	public ArrayList<String> getOptionValues(int id) {
		ArrayList<String> values = new ArrayList<String>();
		Option o = options.get(id);
		if (o == null) return values;
		for (SymbolExt s : o.getVars()) {
			values.add(s.getName());
		}
		return values;
	}
}
