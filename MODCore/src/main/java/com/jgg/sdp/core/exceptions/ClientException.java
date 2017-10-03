package com.jgg.sdp.core.exceptions;

import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.ctes.RC;

public class ClientException extends RuntimeException {

	private static final long serialVersionUID = 6978810622192241395L;

	protected String txt;
    protected int    code;
    protected int    exitCode;
	
	public ClientException() {
		
	}
	
	public ClientException (Exception e) {
		super(e);
		exitCode = RC.FATAL;
		code = MSG.EXCEPTION;
		txt = e.getLocalizedMessage();
	}

}
