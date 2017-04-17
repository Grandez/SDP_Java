package com.jgg.sdp.module.tables;

/**
 * Tabla con las variables que se utilizan para las llamadas dinamicas
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */

import java.util.*;

import com.jgg.sdp.module.items.*;

public class TBCalls {

	private HashMap<String, Copy> deps = new HashMap<String, Copy>();
	
	public void load(ArrayList<Copy> lista) {
		for (Copy d : lista) {
			deps.put(d.getNombre(), d);
		}
	}
	
	public void add(Copy dep) {
		Copy d = deps.get(dep.getNombre());
		if (d == null) {
		   deps.put(dep.getNombre(), dep);
		}
		else {
			d.setRefs(d.getRefs() + 1);
			d.setSubtipo(d.getSubtipo());
			deps.put(d.getNombre(), d);
		}
	}
	
	public Copy getDependence(String name) {
		return deps.get(name);
	}
	
	public ArrayList<Copy> getList() {
		return new ArrayList<Copy>(deps.values());
	}
}
