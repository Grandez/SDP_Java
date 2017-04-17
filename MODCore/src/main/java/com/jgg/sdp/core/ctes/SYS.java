/**
 * Contiene las constantes simbolicas para los valores por defecto
 * Un cambio en estas constantes afectan al sistema completo
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.ctes;

public class SYS {

    // Persistencia
    public static final String PERSISTENCIA = "SDP";
    
    // Variable de entorno para obtener la configuracion de la base de datos
    public static final String DBCONFIG_ENV = "SDP_DBCONFIG"; 

    // Variable de entorno para obtener la configuracion de la base de datos
    public static final String DBCONFIG_FILE = "sdpdb.properties"; 

    // Fichero que contiene las sentencias no permitdas
    public static final String BAD_STMTS = "badStatements"; 
    
    // Usuario del sistema
	public static final String DEF_USER = "System";
	
	// Lenguaje por defecto si no existe el propio
	public static final String DEF_LANG = "XX";
	
	// Cadena para identificar nulos
	public static final String NULL = "N/A";
	
	// Maxima longitud del nombre de un parrafo
	// Afecta a SDPTRAPx
	public static final int    MAX_PARR_NAME = 48;



	public static final long APPL_GENERAL     =  1;  // Id de la aplicacion general
	public static final long APPL_AUTO        =  2;  // Id del area que agrupa las aplicaciones automaticas
	public static final long APPL_NOT_MONITOR =  3;  // Id de la aplicacion para modulos no monitorizados
    public static final long APPL_WEB         =  5;  // Id de la aplicacion web
    
    // Identificador de los modulos web
    public static final long MOD_USER     =  1;
    public static final long MOD_CONFIG   =  2;
	
	
	// Roles
	public static final int ROLE_ADMIN = 1;
	public static final int ROLE_USER  = 0;

	public static final int  CDG_MODULOS = 1; 
	public static final int  CDG_DEPEND  = 2;
    public static final int  CDG_CONFIG  = 3;

    // Nivel de complejidad maximo
    public static final int DEF_CC = 10;
    
    // Maximo de instrucciones por parrafo
    public static final int DEF_STMT = 15;
   
    // Codigo de estado del modulo
    public static final int STATUS_OK        =  0;
    public static final int STATUS_KO        = -1;
    public static final int STATUS_OK_MANUAL =  1;

    // Registro especial de traducciones, indica que se refiera a otro campo
    public static final int FLAG_REDIRECT = 253;
    
    // Registro especial de traducciones, indica que el campo es un flag (0/1)
    public static final int FLAG_FLAG = 254;
    
    // Registro especial de traducciones, indica que el campo es una mascara
    public static final int FLAG_MASK = 255;

    // Registro especial de traducciones generales
    public static final String DEF_XLATE = "SDP";

    // Longitud de los punteros
    public static final int POINTER = 4;
    
    private SYS() {
		
	}
}
