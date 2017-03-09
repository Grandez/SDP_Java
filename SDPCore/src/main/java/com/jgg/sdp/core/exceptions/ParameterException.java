/**
 * Gestor de excepciones en el tratamiento de parametros de la linea de 
 * comandos o de los ficheros de configuracion
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.exceptions;

import com.jgg.sdp.core.ctes.RC;

public class ParameterException extends SDPException {

	private static final long serialVersionUID = 3465149148513898628L;

	/**
     * Genera una nueva excepcion con el codigo de error dado
     *
     * @param errCode  Codigo de error
     */
	public ParameterException(int errCode) {
		exitCode = 8;
		initException(errCode, new Object[] {});
	}

    /**
     * Genera una nueva excepcion con el codigo de error dado
     *
     * @param errCode  Codigo de error
     * @param txt      Texto explicativo     
     *  
     */
	public ParameterException(int errCode, String txt) {
		exitCode = RC.ERROR;
		initException(errCode, new Object[] {txt});
	}

    /**
     * Genera una nueva excepcion con el codigo de error dado
     * y un conjunto de parametros para generar el mensaje de error
     *
     * @param errCode  Codigo de error
     * @param vars     Parametros para la generacion del mensaje
     *  
     */
	public ParameterException(int errCode, Object... vars) {
		exitCode = RC.ERROR;
		initException(errCode, vars);
	}

}
