/**
 * Contiene las constantes simbolicas para acceder a la tabla de mensajes
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.common.ctes;

public class MSG {

	public static final int TITLE_SDP           =    10;	
	public static final int TITLE_SDP_ANALYZER  =    11;
	public static final int TITLE_SDP_TRAPPER   =    12;
	public static final int TITLE_SDP_COLLECTOR =    13;	
	public static final int TITLE_SDP_LOGGER    =    14;
	public static final int TITLE_SDP_IVP       =    15;	
	public static final int TITLE_SDP_RULES     =    16;
	public static final int TITLE_SDP_EXCEL     =    17;
	public static final int TITLE_SDP_LOADER    =    18;
	
	
//	public static final int TITLE_SDP_CICS      =    12;
//
//	public static final int TITLE_SDP_TREE      =    14;
//	
//	public static final int TITLE_SDP_TS        =    16;	
//	public static final int TITLE_SDP_CALL      =    17;
//	public static final int TITLE_SDP_BATCH     =    18;
//	public static final int TITLE_SDP_EXTRACTOR =    19;	

	
	
	
	public static final int USE_SDP_TRAPPER     =    20;	
	public static final int USE_SDP_LITE        =    23;
	public static final int USE_SDP_ANALYZER    =    23;	
	public static final int USE_SDP_IVP         =    24;	
	public static final int USE_SDP_CALL        =    27;	
	public static final int USE_SDP_LOADER      =    28;
	public static final int LANG                =    30;


	public static final int DESC_TRAPPER        =    40;
	public static final int DESC_TRAPPER_41     =    41;
	public static final int DESC_TRAPPER_42     =    42;
	public static final int DESC_TRAPPER_43     =    43;	

	public static final int NO_DATA             =   100;

	public static final int TITLE_SDP_PARSER    =   201;
	public static final int TITLE_OPTIONS       =   202;
	public static final int TITLE_DEFINITION    =   203;	
	
	
	public static final int ENV_HEADER          =   300;
	public static final int ENV_INPUT           =   301;
	public static final int ENV_OUTPUT          =   302;
	public static final int ENV_QUEUE           =   303;
	public static final int ENV_BEGIN           =   304;
	public static final int ENV_END             =   305;

	public static final int PARSED              =   500;
	public static final int PARSING             =   501;
	public static final int PROCESSING          =   502;
	public static final int LOADING             =   503;
	public static final int OK                  =   510;
	public static final int KO                  =   511;
	public static final int SKIP                =   512;
	public static final int NODATA              =   513;
	public static final int IGNORED             =   514;
	public static final int ERROR               =   515;	
	
	public static final int PARSE_ERRORS        =   520;
	
	public static final int PARM_BAD            =  1000;
	public static final int PARM_VALUE_MISSING  =  1001;
	public static final int PARM_BAD_DIR        =  1002;
	public static final int PARM_BAD_FILE       =  1003;
	public static final int PARM_BAD_NUMBER     =  1004;
	public static final int PARM_BAD_RESOURCE   =  1005;
	public static final int PARM_DEFINED        =  1006;
	
	public static final int FILE_NOT_EXIST      =  2001;
	public static final int FILE_NOT_READ       =  2002;
	
	public static final int ERR_NO_QUEUE        =  3100;
	public static final int ERR_NO_QUEUE_OUTPUT =  3101;

	public static final int YES                 = 60001;
	public static final int NO                  = 60002;

	////////////////////////////////////////////////////////////
	// Excepciones
    //////////////////////////////////////////////////////////
	
	public static final int JMS_BASE            = 90000;
	public static final int NO_JMS_PROVIDER     = 90001;
	public static final int JMS_CONNECT         = 90002;
	public static final int JMS_OPEN            = 90010;
	public static final int JMS_GET             = 90011;
	public static final int JMS_PUT             = 90012;

	public static final int EXCEP_NO_LANG       = 91000;
	public static final int EXCEP_NO_LANG_DEF   = 91001;
	public static final int EXCEP_NO_PARSER     = 91002;

	public static final int EXCEP_NO_DIR        = 92001;
	public static final int EXCEP_NO_FILE       = 92002;
	
	public static final int BAD_JMS_PROVIDER    = 92100;
	
	public static final int EXCEP_JAVA_REFLECT  = 95000;
	public static final int EXCEP_JAVA_METHOD   = 95001;
	
	public static final int EXCEPTION_PARSER    = 99001;
	public static final int EXCEPTION_SYNTAX    = 99002;
	public static final int EXCEPTION_TOKEN     = 99003;
	public static final int EXCEPTION_CUP       = 99005;
	public static final int EXCEPTION_NOT_ALLOW = 99006;
	public static final int EXCEPTION_LEXER     = 99007;	
	
	public static final int EXCEPTION_DBCONFIG   = 99500;	
	public static final int EXCEPTION_CONFIG     = 99501;
	public static final int EXCEPTION_FORMAT     = 99504;
	public static final int EXCEPTION_LOCK       = 99505;	
	public static final int EXCEPTION_RULE_GROUP = 99510;

	// Situaciones no permitidas
	public static final int SUPPORT_SECTION     = 98010;
	public static final int SUPPORT_NAME        = 98020;
	public static final int SUPPORT_COPY        = 98021;
	public static final int SUPPORT_CLASS       = 98022;
	public static final int SUPPORT_FACTORY     = 98023;
	public static final int SUPPORT_OBJECT      = 98024;
	public static final int SUPPORT_METHOD      = 98025;
	
	public static final int EXCEPTION_KEY        = 99600;
	public static final int EXCEPTION_KEY_SERVER = 99601;
	
	public static final int FATAL_EXCEPTION     = 99900;
	public static final int EXCEPTION           = 99999;
	
	private MSG() {
		
	}
}