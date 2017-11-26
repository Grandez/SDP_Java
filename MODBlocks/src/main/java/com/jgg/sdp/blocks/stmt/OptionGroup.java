package com.jgg.sdp.blocks.stmt;

import java.util.ArrayList;

import com.jgg.sdp.blocks.reflect.IOptionGroup;

public class OptionGroup implements IOptionGroup {

	private ArrayList<Option> opts = new ArrayList<Option>();

	public OptionGroup() {
	}
	
	public OptionGroup(Option opt) {
		opts.add(opt);
	}
	
	public void addOption(Option opt) {
		opts.add(opt);
	}
	
	public Option getOption() {
		if (opts.size() > 0 ) return opts.get(0);
		return null;
	}
    /***************************************************************/
	/***   INTERFAZ PARA LLAMADAS INTROSPECTION                  ***/
	/***************************************************************/

	public int numOccurs() {
		return opts.size();
	}
}
