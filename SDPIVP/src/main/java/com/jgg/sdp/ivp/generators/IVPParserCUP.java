package com.jgg.sdp.ivp.generators;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.jgg.sdp.ivp.generator.cobol.*;
import com.jgg.sdp.ivp.lang.*;

import java_cup.runtime.Symbol;

public class IVPParserCUP {
	
	public CUPAST  parse() {
		CUPLexer lexer;
		try {
			lexer = new CUPLexer(new FileReader("P:/SDP/Java/MODParserCobol/config/ZCCParser.cup"));
			CUPParser parser = new CUPParser(lexer);
			Symbol raw = parser.parse(); 
			CUPAST ast = (CUPAST) raw.value;
			debug(ast);
			return  ast;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}

	private void debug(CUPAST ast) {
		for (CUPNonTerminal nt : ast.getNonTerminals()) {
			 for (CUPRHS rhs : nt.getProductions()) {
				 System.out.print(nt.getName() + " --> ");
				 for (CUPComponent c : rhs.getRhs()) {
					 System.out.print(c.getName() + " ");
				 }
				 System.out.println();
			 }
		}
	}
}
