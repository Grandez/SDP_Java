/**
 * Contiene las variables simbolicas de los codigos numericos
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.ctes;

public class CDG {

    // Grupos
    
    public static final int MODULOS     =  1;
    public static final int COPYS       =  2;
    
    public static final int MODULE_ACTIVE   = 1;
    public static final int MODULE_INACTIVE = 0;
    
    // Codigos de tipos de ficheros
    
    public static final int SOURCE_UNDEF   = 0;
    public static final int SOURCE_CODE    = 1;
    public static final int SOURCE_COPY    = 2;
    public static final int SOURCE_INCLUDE = 3;
    
    // Codigos de estado del analisis nivel fichero
    public static final int STATUS_UNDEF            =   0;
    // Completo
    public static final int STATUS_FULL             =   1;
    // Faltan COPYS pero no hay problemas de arbol
    public static final int STATUS_PARTIAL_NO_DOUBT =   3;
    // Faltan COPYS o ignoradas
    public static final int STATUS_PARTIAL          =   4;
    
    // Solo se ha analizado la parte CICS
    public static final int STATUS_CICS             =  10;
    
    // Modulo marcado como ignorado
    public static final int STATUS_IGNORED          =  80;
    
    public static final int STATUS_BAD              =  90;
    // Modulo con sentencias no soportadas
    public static final int STATUS_NOT_SUPPORTED    =  91;
    // Error en analisis
    public static final int STATUS_SDP_ERROR        =  95;
    // Error generico
    public static final int STATUS_ERROR            =  99;
    
    
	// Codigos para los tipos de modulos

	public static final int MOD_UNDEF   =   0;
	public static final int MOD_AREA    =   1;
	public static final int MOD_APPL    =   2;
	public static final int MOD_MAIN    =  11;
	public static final int MOD_ROUTINE =  12;
	public static final int MOD_EXEC    =  15;
	
	// Codigos de las secciones de un programa
	// Las divisiones son multiplos de 10
	
	public static final int SECT_UNDEF  =    0;
	public static final int SECT_ID     =   10;
	public static final int SECT_ENV    =   20;
	public static final int SECT_CONF   =   21;	
	public static final int SECT_IO     =   22;	
	public static final int SECT_DATA   =   30;
	public static final int SECT_FILE   =   31;	
    public static final int SECT_WORK   =   32;
    public static final int SECT_LOCAL  =   33;
    public static final int SECT_LINK   =   34;    
	public static final int SECT_PRC    =   40;
	
	public static final int DIV_COUNT   =   5;
	public static final int SECT_COUNT  =   5;	

	// Codigos de estado del arbol de llamadas
	
	public static final int TREE_FULL     = 1;
	public static final int TREE_MISSING  = 0;
	
	// Codigos para las dependencias de otros modulos o miembros
	
    public static final int DEP_UNDEF   = 0;	
	public static final int DEP_COPY    = 1;
	public static final int DEP_INCLUDE = 2;	
    public static final int DEP_MODULE  = 4;
    public static final int DEP_TRAN    = 8;    

	// Codigos de estado de las COPYS/INCLUDE
	
	public static final int CPY_ST_UNDEF     = 0;
	public static final int CPY_ST_FULL      = 1;
	public static final int CPY_ST_MISSING   = 2;
	public static final int CPY_ST_IGNORED   = 3;
	public static final int CPY_ST_ERROR     = 9;

    // Subtipo de la dependencia de COPY

	public static final int CPY_TYPE_UNDEF     =  0;
	public static final int CPY_TYPE_VAR       =  1;
	public static final int CPY_TYPE_LINKAGE   =  2;
	public static final int CPY_TYPE_CODE      =  5;

    public static final int CPY_FILE      = 10;
	public static final int CPY_RECORD    = 11;
	
	public static final int CPY_TABLE     = 20;	
	public static final int CPY_CURSOR    = 21;
	public static final int CPY_STATEMENT = 22;	

	// Tipo de llamada a MODULO. Se usara como mascara
	public static final int CALL_UNDEF   =  0; // Sin definir
	public static final int CALL_CALL    =  1; // CALL
	public static final int CALL_LINK    =  2; // LINK
	public static final int CALL_XCTL    =  4; // XCTL
	public static final int CALL_START   =  8; // START TRANSID
	public static final int CALL_RETURN  = 16; // RETURN TRANSID
	
	// Modo de llamada
	public static final int CALL_NO_DEF  =  0; // No definido
	public static final int CALL_STATIC  =  1; // Modulo estatico
	public static final int CALL_DYNAMIC =  2; // Modulo dinamico identificado
	
	// Estado de la dependencia
	public static final int DEP_ST_UNDEF       =   0; // Indefinido
	public static final int DEP_ST_VARIABLE    =   1; // Variable no analizada
    public static final int DEP_ST_DECLARED    =   2; // Aparece en el codigo	
	public static final int DEP_ST_MANUAL      =   4; // Indicado por usuario	
	public static final int DEP_ST_CHECKED     =   8; // Verificado su uso en run time
	public static final int DEP_ST_NOT_PARSED  =  16; // No se ha analizado (Se usa en el arbol)
	
	// Flags de identificador de fichero maestro
	
	public static final int MASTER_NO   = 0;
	public static final int MASTER_AUTO = 1;
	public static final int MASTER_PGM  = 2;
	public static final int MASTER_ADM  = 3;

	// Rangos de fechas
	public static final int RANGE_ALL     = 0;
	public static final int RANGE_DAY     = 1;
	public static final int RANGE_WEEK    = 2;
	public static final int RANGE_MONTH   = 3;
	public static final int RANGE_QUARTER = 4;

	// Estados de aprobacion
	public static final int ST_PENDING = 0;
	public static final int ST_AUTO    = 1;
	public static final int ST_MANUAL  = 2;

	// Threadsafe
	public static final int TS_UNDEF   = 0;
	public static final int TS_YES     = 1;
	public static final int TS_NOT     = 2;
	public static final int TS_BOTH    = 3;
    public static final int TS_QR      = 6;
	
	
	private CDG() {
		
	}
}
