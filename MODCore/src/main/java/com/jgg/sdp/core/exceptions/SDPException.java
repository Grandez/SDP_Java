/**
 * Gestor de excepciones general del sistema 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.exceptions;

import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.ctes.RC;
import com.jgg.sdp.core.msg.Messages;

public class SDPException extends RuntimeException {

	private static final long serialVersionUID = -8289568191606404903L;

	protected Messages msg = Messages.getInstance();
	
    protected String txt;
    protected int    code;
    protected int    exitCode;
    
	public SDPException() {
		
	}
	
	public SDPException (Exception e) {
		super(e);
		exitCode = RC.FATAL;
		code = MSG.EXCEPTION;
		txt = e.getLocalizedMessage();
	}

	public SDPException (Exception e, int code, String modulo) {
        super(e);
        this.code = code;
        exitCode = RC.FATAL;
        txt = msg.getMsg(code, modulo);
        addMessage("Caused by: " + e.toString());
    }
	
	public SDPException(int code) {
	    this.code = code;
	    txt = msg.getMsg(code, (Object[]) null);	
	}
	
	public SDPException(int code, Object... args) {
	    this.code = code;
	    txt = msg.getMsg(code, args);
	}
	
	public SDPException(String fmt, int code) {
		txt = String.format("SDP%s - %s", strCode(code), fmt);		
	}
	
    /**
     * Genera una nueva excepcion a partir de una cadena de texto
     * que requiere parametros
     *
     * @param fmt    Cadena con el formato del mensaje de error 
     * @param code   El codigo de error
     * @param args   Los parametros para construir el mensaje
     *  
     */
	public SDPException(String fmt, int code, Object... args) {
       String txt = String.format(fmt, args);
       txt = String.format("SDP%s - %s" , strCode(code), txt);
	}

	@Override
	public String getMessage() {
		return txt;
	}

	public int getErrorCode() {
		return code;
	}
	
	public int getExitCode() {
		return exitCode;
	}

	protected void initException(int errCode, Object... vars) {
		code = errCode;
		txt = msg.getMsg(errCode, vars);
	}

	private void addMessage(String msg) {
        txt = txt + "\n" + msg; 	    
	}
	
	private String strCode(int code) {
		return String.format("%05d", code);
	}
	
}
