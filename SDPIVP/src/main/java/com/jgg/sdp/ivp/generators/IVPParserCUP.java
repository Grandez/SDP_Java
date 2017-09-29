package com.jgg.sdp.ivp.generators;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.jgg.sdp.ivp.generator.cobol.*;
import com.jgg.sdp.ivp.lang.*;

import static com.jgg.sdp.ivp.generator.cobol.CUPComponent.*;
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
/*	
	private CUPAST reduceTree(CUPAST ast) {
		int v = 0;
        int t = 0;
        int nt2 = 0;
		boolean done = false;
		while (!done) {
			v++;
			t = 0;
			nt2 = 0;
			done = true;
			for (CUPNonTerminal nt : ast.getNonTerminals()) {
				if (nt.hasProductions()) {
					if (checkNonTerminal(nt, ast)) {
						done = false;
						nt2++;
					}
				}
				else {
					if (!nt.isReduced()) {
						addRHSTerminal(nt, ast);
						done = false;
						t++;						
					}
				}
				if (tryToRHSTerminal(nt, ast)) {
					done = false;
				}
			}
			System.out.println("Vuelta " + v + " - terminales: " + t + " - no terminales: " + nt2);
		}
*/
		/**
		 * Ahora mira las construcciones que han quedado como:
		 * nt ::= t
		 *    |   rhs1
		 *    |   rhs2
		 * y las convierte en rhsnt ::= t | rhs1 | rhs2   
		 */
/*
	reduceNonTerminals(ast);
		
		return ast;
	}

	private void reduceNonTerminals(CUPAST ast) {
		boolean done = false;
		boolean reduced = false;
		while (!done) {
			done = true;
			for (CUPNonTerminal nt : ast.getNonTerminals()) {
				if (nt.isReduced()) {
					CUPRHS n = new CUPRHS();
					for (CUPRHS r : nt.getProductions()) {
						reduced = true;
						for (CUPComponent c : r.getRhs()) {
							switch(c.getType()) {
							   case NON_TERMINAL: reduced = false;         break;
							   case TERMINAL:    n.add(c);		           break;
							   case RHS_TERMINAL: appendRHSTerminal(ast, nt, c, n); break;
							
							}
							if (!reduced) break;
						}
						if (!reduced) break;
					}
					if (reduced) {
						createRHSTerminal(ast, nt, n);
					}
				}
			}
		}
		
	}

	private void createRHSTerminal(CUPAST ast, CUPNonTerminal n, CUPRHS base) {
		CUPRHSTerminal rhs = (CUPRHSTerminal) base;
		for (CUPRHS r : rhs.getProductions()) {
			for (CUPComponent c : r.getRhs()) {
			     n.add(c);
			}
		}
	}

	private void appendRHSTerminal(CUPAST ast, CUPNonTerminal nt, CUPComponent c, CUPRHS base) {
		
		CUPRHSTerminal rhs = addRHSTerminal(ast, nt);
		rhs.setProductions(base);	
		}
	}
	
	private boolean checkNonTerminal(CUPNonTerminal nt, CUPAST ast) {
		boolean prodsChanged = false;
		boolean changed = false;
		for (CUPRHS production : nt.getProductions()) {
			changed = false;
			CUPComponent[] c = production.getProductionAsArray();
			for (int idx = 0; idx < c.length; idx++) {
				if (c[idx].getType() == 2) {
					String rhsName = findRHSTerminal(ast, c[idx].getName()); 
					if (rhsName != null) {
						System.out.println("\t" + nt.getName() + " - " + c[idx].getName() + " --> " + rhsName);
						CUPRHSTerminal rhs = ast.rhsTerminals.get(rhsName);
						c[idx] = rhs;
						
						changed =true;
					}
				}
			}
			if (changed) {
				production.replace(c);
				prodsChanged = true;
			}
		}
		return prodsChanged;
	}

	private String findRHSTerminal(CUPAST ast, String name) {
		String rhs = ast.dictionary.get(name);
		String prev = rhs;
		while (rhs != null) {
			prev = rhs;
			rhs = ast.dictionary.get(rhs);
		}
		return prev;
	}
	
	private void addRHSTerminal(CUPAST ast, CUPNonTerminal nt) {
		CUPRHSTerminal rhs = new CUPRHSTerminal(nt);
		ast.dictionary.put(nt.getName(), rhs.getName());
		ast.rhsTerminals.put(rhs.getName(), rhs);
		nt.setReduced(true);
	}
	
*/
	
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
