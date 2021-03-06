package com.jgg.sdp.core.exceptions;

import com.jgg.sdp.common.exceptions.SDPException;

public class FormatException extends SDPException {
	
	private static final long serialVersionUID = 8357216899986776717L;

	public FormatException(int code, Object... args) {
		this.code = code;
	    txt = msg.getMsg(code,  args);
	}

}
