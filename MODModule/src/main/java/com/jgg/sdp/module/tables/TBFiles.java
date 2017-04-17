/**
 * Mantiene la tabla de la lista de fichero
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tables;

/**
 * Se podrian utilizar hash para en principio optimizar la busqueda
 * Pero los hash tambien tienen un coste alto y no se esperan muchos ficheros
 * por lo que la busqueda se hara secuencial
 */

import java.util.*;

import com.jgg.sdp.module.items.Persistence;

public class TBFiles {

	private ArrayList<Persistence>       ficheros = new ArrayList<Persistence>();
	
	/**
     * Inserta un fichero
     *
     * @param f
     *            El fichero a insertar
     */
	public void addFile(Persistence f) {
		ficheros.add(f);
		f.setPos(ficheros.size() - 1);
	}
	
	/**
     * Obtiene el objeto fichero por su nombre
     *
     * @param name
     *            Nombre del fichero
     * @return El objeto asociado
     */
	public Persistence getFile(String name) {
		return findByName(name);
	}

	/**
     * Obtiene el objeto fichero por su descriptor de registro
     *
     * @param name
     *            El nombre del descriptor 
     * @return El objeto asociado
     */
	public Persistence getFileByRecord(String name) {
		return findByRecordName(name);
	}
	
	public int getCount() {
		return ficheros.size();
	}
	
	public ArrayList<Persistence> getFiles() {
		return ficheros;
	}
	
	private Persistence findByName(String name) {
		Persistence p;
		for (int idx = 0; idx < ficheros.size(); idx++) {
			p = ficheros.get(idx);
			if (p != null) {
			    if (p.getLogicalName().compareToIgnoreCase(name) == 0) {
				    return ficheros.get(idx);
			    }
			}
		}
		return null;
	}

	private Persistence findByRecordName(String name) {
		Persistence p;
		for (int idx = 0; idx < ficheros.size(); idx++) {
			p = ficheros.get(idx);
			if (p != null) {
				for (String record : p.getRecordNames()) {
			       if (record.compareToIgnoreCase(name) == 0) {
				       return p;
			       }
				}
			}
		}
		return null;
	}

}
