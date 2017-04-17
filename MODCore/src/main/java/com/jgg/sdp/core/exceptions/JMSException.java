/**
 * Gestor de excepciones del sistema de mensajeria
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.exceptions;

import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.ctes.RC;

public class JMSException extends SDPException {

    private static final long serialVersionUID = -1983183946815937521L;

    /**
     * Genera una nueva excepcion de mensajeria 
     *
     * @param code Codigo de error asociado
     * @param parms Parametros para construir el mensaje de error
     */
    public JMSException(int code, Object... parms) {
        super(code, parms);
        this.code = (Integer) parms[0] + MSG.JMS_BASE;
        exitCode = RC.FATAL;
    }
}
