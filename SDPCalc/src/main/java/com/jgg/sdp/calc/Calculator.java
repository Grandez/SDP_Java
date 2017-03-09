package com.jgg.sdp.calc;

import java.math.BigDecimal;
import java.util.HashMap;

import com.jgg.sdp.calculator.CalcLexer;
import com.jgg.sdp.calculator.CalcParser;

import java_cup.runtime.Symbol;

public class Calculator {

	Reader reader = null;
    HashMap<String, Integer> vars = null;
	
	public Calculator() {
	}
	
	public Calculator(String data) {
	    reader = new Reader(data + ";");	
	}

	public Calculator(String data, HashMap<String, Integer> vars) {
	    reader = new Reader(data + ";");
	    this.vars = vars;
	}
	
	public void setData(String data) {
		reader = new Reader(data + ";");
	}
	
	public void setVars(HashMap<String, Integer> vars) {
		this.vars= vars; 
	}

	public boolean evaluateLogicalExpression() throws Exception {
		Symbol s = parse();
		Symbol val = (Symbol) s.value;
		if (val.value instanceof Boolean) {
			return (Boolean) val.value;
		}
		return false;
	}
	
	public BigDecimal evaluateExpression() throws Exception {
		Symbol s = parse();
		return (BigDecimal) ((Symbol) s.value).value;
	}
	
	private Symbol parse() throws Exception {
		CalcLexer lexer = new CalcLexer(reader);
		CalcParser parser = new CalcParser(lexer);
		parser.setVariables(vars);
		return parser.parse();
//		return parser.debug_parse();
	}
}
