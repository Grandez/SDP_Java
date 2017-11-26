package com.jgg.sdp.core.exceptions;

import com.jgg.sdp.common.exceptions.SDPException;

import static com.jgg.sdp.common.ctes.MSG.*;
import        com.jgg.sdp.common.ctes.RC;

public class JavaException extends SDPException {

	private static final long serialVersionUID = -8370201455100651954L;

	public JavaException(String... parms) {
        super(EXCEP_JAVA_REFLECT, (Object[]) parms);
        this.code = EXCEP_JAVA_REFLECT;
        exitCode = RC.FATAL;
    }

	public JavaException(int num, String... parms) {
        super(EXCEP_JAVA_METHOD, (Object[]) parms);
        this.code = EXCEP_JAVA_METHOD;
        exitCode = RC.FATAL;
    }

}
