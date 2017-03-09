/**
 * Gestor de excepciones en el tratamiento de ficheros
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */

package com.jgg.sdp.core.exceptions;

public class FileException extends SDPException {

	private static final long serialVersionUID = -6190740320625613598L;

	public FileException(int errCode) {
		exitCode = 16;
		initException(errCode, new Object[] {});
	}

	public FileException(int errCode, String txt) {
		initException(errCode, new Object[] {txt});
	}

	public FileException(int errCode, Object... vars) {
		initException(errCode, vars);
	}

}
