/**
 * Una instruccion SIEMPRE empieza por un verbo
 * stmt ::= VERBO cosas
 */
package com.jgg.sdp.ivp.generators;

import java.util.Iterator;
import java.util.Stack;

import com.jgg.sdp.ivp.generator.cobol.*;

public class IVPGeneratorStmt {

	private CUPComponent[]  production;
	private StringBuilder[] pieces= null;
	
	public static void main(String[] args) throws Exception {
		   int rc = 0;
		   IVPGeneratorStmt launcher = new IVPGeneratorStmt();
		   rc = launcher.start(args);
	       System.exit(rc);
		}

	private int start(String[] args) {
		CUPAST ast = parseLanguage();
		generatePrograms(ast);
		return 0;
	}
	
	private CUPAST parseLanguage() {
		IVPParserCUP parser = new IVPParserCUP();
		CUPAST ast = parser.parse();
		return ast;
	}

	
	/**
	 * Solo procesamos las instraucciones que empiecen con stmt
	 * @param ast
	 */
	private void generatePrograms(CUPAST ast) {
		// Para cada statement
		
//		CUPNonTerminal root = ast.nonTerminals.get("stmtAdd");
		processStatement(ast.nonTerminals.get("stmtTest"));
	}
	
	private void processStatement(CUPNonTerminal root){
		// Solo hay 1
		for (CUPRHS rhs : root.getProductions()) {
	    	production = rhs.getProductionAsArray();
			pieces = new StringBuilder[production.length];
//	    	System.out.print("Regla: " + root.getName() + ": ");
//	    	for (int idx = 0; idx < production.length; idx++) {
//	    		System.out.print(production[idx].getName() + " ");
//	    	}
	    	System.out.println("");
	    	processRule(0);
		}		
	}

	/**
	 * Empieza en el 0 y va llamando a las siguientes
	 * @param rhi
	 */
    private boolean processRule(int rhi) {
    	boolean processed = false;

    	if (rhi == production.length) {
    		writeSentence();
    		return true;
    	}
    	initSentence(rhi);
    	
    	CUPComponent c = production[rhi];
    	if (c instanceof CUPTerminal) {
    		pieces[rhi].append(processTerminal((CUPTerminal) c, rhi));
    	}
    	else {
    		processed = processNonTerminal((CUPNonTerminal) c, rhi);
    	}
    	
    	if (!processed) processed = processRule(rhi + 1);
    	return processed;
    }

    private String processTerminal(CUPTerminal term, int rhi) {
		return term.getName();
    }
      
    private boolean processNonTerminal (CUPNonTerminal nt, int rhi) {
        boolean processed = processNonTerminalChild (nt, rhi);
        return processed;
    }
    
    private boolean processNonTerminalChild (CUPNonTerminal nt, int rhi) {
    	boolean processed = false;
    	for (CUPRHS rhs : nt.getProductions()) {
    		processed = false;
    		initSentence(rhi);
    		for (CUPComponent c : rhs.getRhs()) {
    	    	if (c instanceof CUPTerminal) { 
                    pieces[rhi].append(processTerminal((CUPTerminal) c, rhi));
                    pieces[rhi].append(" ");
    	    	}
    	    	else {
    	    		processed = processNonTerminalChild((CUPNonTerminal) c, rhi);
    	        	//System.out.println("Estoy en " + nt.getName());
    	    	}
    	   }
    	   if (!processed) processed = processRule(rhi + 1);
    	}		
    	return processed;
    }
    
    private void writeSentence() {

    	System.out.print("Sentence: ");
    	for (int idx = 0; idx < pieces.length; idx++) {
    		System.out.print(pieces[idx]);
    		System.out.print(" ");
    	}
    	System.out.println("");
    }
    
	private void initSentence(int from) {
		for (int idx = from; idx < pieces.length; idx++) {
			pieces[idx] = new StringBuilder();
		}
	}
	

}
