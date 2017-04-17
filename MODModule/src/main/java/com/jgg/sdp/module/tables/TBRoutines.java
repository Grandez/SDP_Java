/**
 * Implementa la lista de rutinas llamadas
 *   
 * Puede ocurrir que una rutina se llame de varias formas (CALL y LINK)
 * o que la variable aparezca varias veces
 * 01 rutina pic x(08) value "rutina"
 * 
 * CALL "rutina"
 * CALL rutina
 * 
 * Por eso mantenemos dos listas
 *    
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tables;

import java.util.*;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.module.items.Routine;

public class TBRoutines {

	private HashMap<String, Routine> rutDeclared = new HashMap<String, Routine>();
	private HashMap<String, Routine> rutChecked  = new HashMap<String, Routine>();
	
	public Routine get(String name) {
		return rutDeclared.get(name);
	}

	public void add(Routine rut) {
		if (rut.getModo() == CDG.CALL_STATIC) {
			add(rutChecked, rut);
		}
		else {
			add(rutDeclared, rut);
		}
	}
	
	public void addChecked(Routine rut) {
		add(rutChecked, rut);
	}
	
	private void add(HashMap<String, Routine> table, Routine rut) {
		Routine r = table.get(rut.getNombre());
		if (r != null) {
			r.setRefs(r.getRefs() + 1);
			r.setMetodo(rut.getMetodo());
			r.setModo(rut.getModo());
		}
		else {
			table.put(rut.getNombre(), rut);
		}
	}
	
	public void add(String name, int type, int subtype) {
		add(new Routine(name, type, subtype));
	}
	
	public ArrayList<Routine> getRoutines() { 
		return new ArrayList<Routine>(rutDeclared.values());
	}

	public ArrayList<Routine> getDynamicRoutines() {
		return new ArrayList<Routine>(rutDeclared.values());
	}
	public ArrayList<Routine> getCheckedRoutines() {
		return new ArrayList<Routine>(rutChecked.values());
	}

}
