/**
 * Factoria para obtener el analizador sintactico adecuado al 
 * dialecto COBOL
 *  
 * Actualmente solo devuelve el analizador sintactico de OpenCOBOL
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.base;

import com.jgg.sdp.parser.lang.OCParser;

public class CobolParserFactory {

	/**
     * Devuelve la instancia del analizador sintactico correspondiente
     * Actualmente solo devuelve el analizador sintactico de OpenCOBOL
     *
     * @param lexer El analizador lexico que debe utilizar
     * @return La instancia del analizador sintactico
     */
	public static OCParser getParser(CobolLexer lexer) {
		return new OCParser((java_cup.runtime.Scanner) lexer);
	}

}
