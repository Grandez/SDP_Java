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
    
	// Codigos para los tipos de modulos

	public static final int MOD_UNDEF   =   0;
	public static final int MOD_AREA    =   1;
	public static final int MOD_APPL    =   2;
	public static final int MOD_MAIN    =  11;
	public static final int MOD_ROUTINE =  12;
	public static final int MOD_EXEC    =  15;
	
	// Codigos para las dependencias de modulos
	
	public static final int DEP_COPY    = 1;
    public static final int DEP_MODULO  = 2;
	
    // Subtipo de la dependencia de COPY
	public static final int DEP_CPY     = 10;
	public static final int DEP_FILE    = 11;
	public static final int DEP_INCLUDE = 12;	
	public static final int DEP_VAR     = 13;
	public static final int DEP_LINK    = 14;
	public static final int DEP_CODE    = 15;
	
	// Subtipo de la dependencia de MODULO
	public static final int DEP_MOD         = 20; // Variable no identificada
	public static final int DEP_MOD_STATIC  = 21; // Modulo estatico
	public static final int DEP_MOD_DYNAMIC = 22; // Modulo dinamico identificado
	public static final int DEP_MOD_UNKNOW  = 25; // Modulo dinamico no identificado

	// Estado de la dependencia
	public static final int DEP_NOT_CHECKED =  0; // Declarado en el codigo
    public static final int DEP_DECLARED    =  1; // Aparece en el codigo	
	public static final int DEP_CHECKED     =  2; // Verificado su uso
	public static final int DEP_UNDEFINED   = 10; // No se conoce se valor
	
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
	
	private CDG() {
		
	}
}
