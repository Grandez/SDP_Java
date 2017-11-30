/**
 * Gestor de excepciones para componentes del lenguaje no soportados
 * en la version actual del sistema
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.parser.base;

import com.jgg.sdp.common.ctes.RC;
import com.jgg.sdp.common.exceptions.SDPException;

public class NotSupportedException extends SDPException {

    private static final long serialVersionUID = 5765396505216401883L;

    /**
     * Genera una nueva excepcion con el codigo de eror indicado
     *
     * @param code   Codigo de error
     * @param module Nombre del fichero fuente donde se ha producido el error
     * @param parms  Parametros para generar el mensaje detallado
     */
    public NotSupportedException(int code, Object... parms) {
        super(code, parms);
        exitCode = RC.SEVERE;
    }
}
