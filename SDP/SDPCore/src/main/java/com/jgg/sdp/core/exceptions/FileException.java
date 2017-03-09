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

	/**
     * Genera una nueva excepcion con el codigo de error indicado
     *
     * @param errCode Codigo de error
     */
	public FileException(int errCode) {
		exitCode = 16;
		initException(errCode, new Object[] {});
	}

	/**
     * Genera una nueva excepcion con el codigo de error indicado
     * y un texto explicativo
     *
     * @param errCode Codigo de error
     * @param txt     Texto explicativo
     */
	public FileException(int errCode, String txt) {
		initException(errCode, new Object[] {txt});
	}

    /**
     * Genera una nueva excepcion con el codigo de error indicado
     * y un conjunto de parametros para la generacion del mensaje
     * de error
     *
     * @param errCode Codigo de error
     * @param vars    Lista de parametros para construir el mensaje
     */
	public FileException(int errCode, Object... vars) {
		initException(errCode, vars);
	}


}
