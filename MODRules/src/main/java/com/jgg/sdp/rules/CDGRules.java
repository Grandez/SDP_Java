package com.jgg.sdp.rules;

public class CDGRules {

	public final static int GRP_ROOT       =   1;
	public final static int GRP_LEXER      =   2;
	public final static int GRP_COMMENT    =  21;
	public final static int GRP_DIRECTIVES =  22;
	public final static int GRP_MODULE     =   9;
	public final static int GRP_ISSUES     =  91;
	
	public final static int TYPE_VERB    = 1;
	public final static int TYPE_OPTION  = 2;
	public final static int TYPE_LVALUE  = 3;
	public final static int TYPE_RVALUE  = 4;
	public final static int TYPE_METHOD  = 5;
	public final static int TYPE_FORMULA = 6;
	public final static int TYPE_VALUE   = 7;	
	public final static int TYPE_SPECIAL = 9;	
	
	// Si es mayor de 10 incluye igual
	// Si es amyor de 100 es la negacion
	public final static int OP_EXIST     =  1;
	public final static int OP_EQ        = 12;
	public final static int OP_GT        =  3;
	public final static int OP_LT        =  4;
	public final static int OP_START     =  7;
	public final static int OP_END       =  8;
	public final static int OP_CONTAINS  =  9;
	public final static int OP_MATCH     = 10;	
	public final static int OP_NOT_MATCH = 11;	
	
	public final static int OP_EQ_TOO    =  10;
	public final static int OP_NEGATED   = 100;
	

	public final static int STAT_OK      =  0;
	public final static int STAT_KO      =  1;
	public final static int STAT_EXCEP   = -1;
	
	public final static int ACTIVE       =  0;
	public final static int INACTIVE     = -1;
	public final static int INHERIT      = -2;
	
	public final static int DESC_NA      =  0;
	
}
