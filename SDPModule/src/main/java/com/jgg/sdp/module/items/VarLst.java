/**
 * Implementa la tabla de control de variables
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

import java.util.ArrayList;

/**
 * Las variables pueden tener el mismo nombre pero distinto padre
 * por eso las guardamos en una lista 
 *
 */

public class VarLst {

	ArrayList<Var> lista = new ArrayList<Var>();
	
	public VarLst(Var var) {
		lista.add(var);
	}
	
}
