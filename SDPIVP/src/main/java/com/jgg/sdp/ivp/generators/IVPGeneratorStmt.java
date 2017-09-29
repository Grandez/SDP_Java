package com.jgg.sdp.ivp.generators;

import com.jgg.sdp.ivp.generator.cobol.*;

public class IVPGeneratorStmt {

	private CUPComponent[] production;
	private String[] words = new String[100];
	private int[]    iWords = new int[100];
    int nWords;
    	
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
		processStatement(ast.nonTerminals.get("stmtAdd"));
	}
	
	private void processStatement(CUPNonTerminal root){
		for (CUPRHS rhs : root.getProductions()) {
	    	production = rhs.getProductionAsArray();

	    	processProduction();
/*	    	
			sb1.append("stmtAdd = ");
			if (rhs.isProduction()) {
				stmt = new StringBuilder();
				processRule(rhs);
			}
			else {
				sb1.append(" " + rhs.getName());
			}
			System.out.println(sb1);
*/			
		}
		
	}

	private void processProduction() {
		nWords = 0;
		processRule(0);
	}
	
	/**
	 * Empieza en el 0 y va llamando a las siguientes
	 * @param rhi
	 */
    private void processRule(int rhi) {
    	if (rhi == production.length) {
    		writeSentence();
    		return;
    	}

    	nWords = iWords[rhi];
    	CUPComponent c = production[rhi];
    	if (c instanceof CUPTerminal) { 
    	    processTerminal((CUPTerminal) c, rhi);
    	    processRule(rhi +1);
    	}
    	else {
            processNonTerminal((CUPNonTerminal) c, rhi);
    	}
/*    	
    	if (c instanceof CUPTerminal) {
    	   if ( 
    		   nWords = iWords[rhi];
    	}
    	else {
    		
    	}
    	processRule(rhi + 1);
*/    	
    }

    private boolean processTerminal(CUPTerminal term, int rhi) {
    	System.out.println("Procesando " + term.getName());
    	words[nWords++] = term.getName();
    	words[nWords] = null;
    	iWords[rhi +1 ] = nWords; 
//    	idxRule++;
//    	writeSentence();
//		if (rhi == (production.length - 1)) {
//			writeSentence();
//			return true;
//		}
		return false;
    }
      
    private void processNonTerminal (CUPNonTerminal nt, int rhi) {
    	System.out.println("Procesando " + nt.getName());
    	if (nt.getName().compareTo("option_giving") == 0) {
    		writeSentence();
    	}
    	for (CUPRHS rhs : nt.getProductions()) {
    		for (CUPComponent c : rhs.getRhs()) {
    	    	if (c instanceof CUPTerminal) { 
                    processTerminal((CUPTerminal) c, rhi);
    	    	}
    	    	else {
    	    		processNonTerminal((CUPNonTerminal) c, rhi);
    	    	}
    	   }
    	   processRule(rhi +1);
    	}		
    }
    
    private void writeSentence() {
    	StringBuilder sb = new StringBuilder();
    	int i = 0;
    	while (words[i] != null) {
        	sb.append(words[i++]);
        	sb.append(" ");
        }
        System.out.println(sb);
    }
}
