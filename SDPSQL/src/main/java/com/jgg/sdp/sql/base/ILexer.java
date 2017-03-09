/**
 * Interfaz que deben implementar los diferentes analizadores lexicos
 *  
 * Establecer el path absoluto del fichero
 * Obtener el path del fichero
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.sql.base;

public interface ILexer {

	public String getFullName();
	public void   setFullName(String fullName);
}
