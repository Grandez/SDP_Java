package com.jgg.sdp.rules.ctes;

public class CDGRules {
	
	public final static int TYPE_NONE          =  0;
	public final static int TYPE_TYPE          =  1;
	public final static int TYPE_PROPERTY      =  2;
	public final static int TYPE_ATTRIBUTE     =  3;
	public final static int TYPE_METHOD        =  4;
	public final static int TYPE_FUNCTION      =  5;
	public final static int TYPE_EXPRESSION    =  6;
	public final static int TYPE_SCRIPT        =  7;
	public final static int TYPE_EXIST         =  8;	
	public final static int TYPE_RSCRIPT       =  9;
	public final static int TYPE_CONFIG        = 10;

	
	public final static int TYPE_VALUE      = 12;	
	public final static int TYPE_VARIABLE   = 13;
	
	public final static int TYPE_STRING    = 101;
	public final static int TYPE_INTEGER   = 102;
	public final static int TYPE_LONG      = 103;
	public final static int TYPE_BOOLEAN   = 104;
	public final static int TYPE_DECIMAL   = 105;
	public final static int TYPE_DATE      = 106;
	public final static int TYPE_TIME      = 107;
	public final static int TYPE_TIMESTAMP = 108;

	public final static int TYPE_VERB     = 201;
	public final static int TYPE_LVALUE   = 202;
	public final static int TYPE_RVALUE   = 203;
	public final static int TYPE_OPTION   = 204;
	public final static int TYPE_LIST     = 205;
	
	// Si es mayor de 10 incluye igual
	// Si es mayor de 100 es la negacion

	public final static int OP_NEGATED   =    -1;
	public final static int OP_POSITIVE  =     1;

	public final static int OP_GENERIC   =     0;
	
	public final static int OP_TYPES     =  1000;
	public final static int OP_STRING    =  OP_TYPES * 1;
	public final static int OP_NUMERIC   =  OP_TYPES * 2;
	public final static int OP_BOOLEAN   =  OP_TYPES * 3;
	public final static int OP_OBJECT    =  OP_TYPES * 4;
	public final static int OP_DECIMAL   =  OP_TYPES * 5;
	
	public final static int MASK_EQ = 1;
	public final static int MASK_LT = 2;
	public final static int MASK_GT = 4;
	
	public final static int OP_EXIST     =  OP_OBJECT   + 1;
	public final static int OP_HAS       =  OP_OBJECT   + 1;
	public final static int OP_EQ        =  OP_NUMERIC  + MASK_EQ;
	public final static int OP_GT        =  OP_NUMERIC  + MASK_GT;
	public final static int OP_LT        =  OP_NUMERIC  + MASK_LT;
	public final static int OP_START     =  OP_STRING   + 1;
	public final static int OP_END       =  OP_STRING   + 2;
	public final static int OP_CONTAINS  =  OP_STRING   + 3;
	public final static int OP_MATCH     =  OP_STRING   + 4;	
	public final static int OP_NOT_MATCH =  OP_STRING   + 5;	
	


	public final static int STAT_OK      =  0;
	public final static int STAT_KO      =  1;
	public final static int STAT_EXCEP   = -1;
	
	public final static long ACTIVE      =  1;
	public final static long INACTIVE    = -1;
	public final static long INHERIT     = -2;
	
	public final static int DESC_NA      =  0;
	
}
