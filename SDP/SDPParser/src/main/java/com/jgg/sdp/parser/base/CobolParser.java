/**
 * Interfaz que deben implementar los analizdores sintacticos 
 *  
 * Solo requiere el metodo parse
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.base;

import java_cup.runtime.Symbol;

public interface CobolParser {

	/**
     * Cada vez que se invoca este metodo se devuelve un Symbol
     *
     * @return El Symbol obtenido
     */
	public Symbol parse();
}
