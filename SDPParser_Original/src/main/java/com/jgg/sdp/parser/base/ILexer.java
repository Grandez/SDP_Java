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
package com.jgg.sdp.parser.base;

import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.unit.SDPUnit;

public interface ILexer {

	public void   setParseUnit(SDPUnit unit);
	public void setModule(Module module);
	public void setIgnoreReserved();
    public void unsetIgnoreReserved();
    public boolean isIgnoreReserved();

}
