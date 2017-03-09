/**
 * Factoria para obtener el analizador lexico adecuado al 
 * dialecto COBOL
 *  
 * Actualmente solo devuelve el analizador lexico de OpenCOBOL
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.base;

import com.jgg.sdp.module.items.Source;
import com.jgg.sdp.parser.lang.OCLexer;

public class CobolLexerFactory {

	/**
     * Obtiene una isntancia del analizador lexico
     *
     * @param source El objeto Source para inicializar el analizador
     * @return la instancia del analizador lexico correspondiente
     */
	public static CobolLexer getLexer(Source source) {
		return new OCLexer(source);
	}
}
