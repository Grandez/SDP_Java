/**
 * Contiene las constantes simbolicas para acceder a la tabla de mensajes
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.ctes;

public class MSG {

	public static final int NO_DATA             =   100;

	public static final int TITLE_SDP_PARSER    =   201;
	public static final int TITLE_OPTIONS       =   202;
	
	public static final int ENV_HEADER          =   300;
	public static final int ENV_INPUT           =   301;
	public static final int ENV_OUTPUT          =   302;
	public static final int ENV_QUEUE           =   303;
	public static final int ENV_BEGIN           =   304;
	public static final int ENV_END             =   305;

	public static final int PARSED              =   500;
	public static final int PARSING             =   501;
	public static final int PARSE_ERRORS        =   510;
	
	public static final int PARM_BAD            =  1000;
	public static final int PARM_VALUE_MISSING  =  1001;
	public static final int PARM_BAD_DIR        =  1002;
	public static final int PARM_BAD_FILE       =  1003;
	public static final int PARM_BAD_NUMBER     =  1004;

	public static final int FILE_NOT_EXIST      =  2001;
	public static final int FILE_NOT_READ       =  2002;
	
	public static final int ERR_NO_QUEUE        =  3100;
	public static final int ERR_NO_QUEUE_OUTPUT =  3101;
	
	public static final int JMS_BASE            = 90000;
	public static final int NO_JMS_PROVIDER     = 90001;
	public static final int JMS_CONNECT         = 90002;
	public static final int JMS_OPEN            = 90010;
	public static final int JMS_GET             = 90011;
	public static final int JMS_PUT             = 90012;
	public static final int BAD_JMS_PROVIDER    = 92100;

	// Situaciones no permitidas
	public static final int SUPPORT_SECTION     = 98010;
	public static final int SUPPORT_NAME        = 98020;
	public static final int SUPPORT_COPY        = 98021;
	
	// Excepciones
	public static final int EXCEPTION_PARSER    = 99001;
	public static final int EXCEPTION_SYNTAX    = 99002;
	public static final int EXCEPTION_TOKEN     = 99003;
	public static final int EXCEPTION_CUP       = 99005;
	public static final int EXCEPTION_NOT_ALLOW = 99006;

	public static final int EXCEPTION_DBCONFIG  = 99500;	

	public static final int EXCEPTION           = 99999;
	
	private MSG() {
		
	}
}