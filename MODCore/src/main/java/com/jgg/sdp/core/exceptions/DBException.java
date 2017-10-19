package com.jgg.sdp.core.exceptions;

import com.jgg.sdp.common.exceptions.SDPException;

public class DBException extends SDPException {

	private static final long serialVersionUID = 1211155012414484209L;

	public DBException(Exception e) {
		super(e);
	}
}
