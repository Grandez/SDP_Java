/**
 * Gestor de excepciones del analizador COBOL 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.exceptions;

public class ParseException extends SDPException {

	private static final long serialVersionUID = 3963070308459438034L;

    /**
     * Genera una nueva excepcion con el codigo de error dado
     * y un conjunto de parametros para generar el mensaje de error
     *
     * @param code     Codigo de error
     * @param vars     Parametros para la generacion del mensaje
     *  
     */
	public ParseException(int code, Object... args) {
	    this.code = code;
	    txt = msg.getMsg(code,  args);
	}
	
    /**
     * Genera una nueva excepcion encadenando otra excepcion anterior
     *
     * @param e  La excepcion original
     *  
     */
	public ParseException(Exception e) {
		txt = e.getMessage();
		exitCode = 2;
		code = 3333;
	}
}
