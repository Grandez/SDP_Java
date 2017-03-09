package com.jgg.sdp.parser.blocks;

import java_cup.runtime.Symbol;

public class BlockID extends BlockCode {

	public BlockID() {
		super("IDENTIFICATION");
	}
	
	public BlockID(Symbol s) {
		super(s);
		name = "IDENTIFICATION";
	}

}
