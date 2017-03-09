/**
 * Define los posibles codigos de retorno al sistema
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.ctes;

public class RC {

	public static final int OK      =  0;
	public static final int BAD_PRM =  1;
	public static final int NOT_SUP =  2;
	public static final int NO_DATA =  4;
	public static final int WARNING =  4;
	public static final int SEVERE  = 12;
	public static final int ERROR   = 16;
	public static final int FATAL   = 32;
	public static final int HELP    =  0;
	
	private RC() {
		
	}
}
