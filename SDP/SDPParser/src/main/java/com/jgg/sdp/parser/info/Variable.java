/**
 * Implementa un objeto Variable con informacion acerca de su uso<br> 
 *
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.info;

import java_cup.runtime.*;

public class Variable extends Symbol {
	
	private Variable padre = null;
	
	public Variable(Symbol s) {
		super(s.sym, s.left, s.right, s.value);
	}

	public Variable(Symbol s, Variable padre) {
		super(s.sym, s.left, s.right, s.value);
		this.padre = padre;
	}
	
	/**
     * Indica cual es su variable padre<br>
     * Niveles no 01
     *
     * @param p La variable Padre
     * @return La variable p
     */
	public Variable addPadre(Variable p) {
		padre = p;
		return p;
	}
	
	public Symbol getPadre() {
		return padre;
	}
}
