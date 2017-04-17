package com.jgg.sdp.calculator;

import java_cup.runtime.Symbol;

public class CalculatorException extends Exception {
	
	private static final long serialVersionUID = 2814275270116253902L;

	private int pos;
	
	public CalculatorException(Symbol s, String msg) {
	    super(msg);
	    pos = s.left;
	}
	
	public int getPosition() {
		return pos;
	}

}
