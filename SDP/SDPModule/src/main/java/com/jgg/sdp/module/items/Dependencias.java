/**
 * Implementa la lista de dependencias (COPYS y Rutinas)
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

import java.util.ArrayList;

public class Dependencias {

	private ArrayList<String>  nombre  = new ArrayList<String>();
	private ArrayList<Integer> tipo    = new ArrayList<Integer>();
	private ArrayList<Integer> subtipo = new ArrayList<Integer>();
	
	/**
     * Añade una dependencia a la lista
     *
     * @param name
     *            Nombre de la dependencia
     * @param type
     *            Tipo de dependencia
     * @param subtype
     *            Subtipo de dependencia
     */
	public void addDependencia(String name, int type, int subtype) {
	    for (int idx = 0; idx < nombre.size(); idx++) {
	        if (nombre.get(idx).compareToIgnoreCase(name) == 0) return;
	    }
		nombre.add(name);
		tipo.add(type);
		subtipo.add(type);
	}
	
	/**
     * Devuelve el numero de dependencias
     *
     * @return Numero de dependencia
     */
	public int numDependencias() {
	    return nombre.size();
	}
	
	/**
     * Obtiene el nombre de la dependencia 
     *
     * @param index
     *            Indice a la lista de dependencias
     * @return Nombre de la dependencia
     */
	public String getNombre(int index) {
	    return nombre.get(index);
	}
	
	/**
     * Devuelve el tipo de la dependencia
     *
     * @param index Indice de la dependencia
     * @return      Tipo de la dependencia
     */
	public Integer getTipo(int index) {
	    return tipo.get(index);
	}

    /**
     * Devuelve el subtipo de la dependencia
     *
     * @param index Indice de la dependencia
     * @return      Tipo de la dependencia
     */
	public Integer getSubTipo(int index) {
        return subtipo.get(index);
    }

}
