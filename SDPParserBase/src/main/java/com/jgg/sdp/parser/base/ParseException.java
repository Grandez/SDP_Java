/**
 * Gestor de excepciones del analizador COBOL 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.parser.base;

import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.exceptions.SDPException;

public class ParseException extends SDPException {

	private static final long serialVersionUID = 3963070308459438034L;

	public ParseException(int code, Object... args) {
	    this.code = code;
	    txt = msg.getMsg(code,  args);
	}
	
	public ParseException(Exception e) {
		txt = e.getMessage();
		exitCode = 2;
		code = 3333;
	}
	
	public ParseException(String message) {
		this.code = MSG.EXCEPTION_LEXER;
		txt = message;
	}
}
