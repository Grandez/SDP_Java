/**
 * Implementa la lista de dependencias (COPYS y Rutinas)
 *   
 * Puede ocurrir que una dependencia aparezca varias veces
 * o que tenga el mismo nombre pero diferente tipo (COPY y Modulo)
 * Por eso utilizamos un conjunto y los objetos Dependence implementan
 * hashcode y equal basado en nombre y tipo
 *    
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tables;

import java.util.*;

import com.jgg.sdp.module.items.Copy;

public class TBCopys {

	private HashMap<String, Copy> depCpy = new HashMap<String, Copy>();
	
	public Copy get(String name) {
		return depCpy.get(name);
	}

	public Copy add(Copy dep) {
		Copy d = get(dep.getNombre());
		if (d != null) {
			d.incRefs();
		}
		else {
			depCpy.put(dep.getNombre(), dep);
		}
		return dep;
	}
	
	public void add(String name, int type, int subtype) {
		add(new Copy(name, type, subtype));
	}
	
	public ArrayList<Copy> getCopys() { 
		return new ArrayList<Copy>(depCpy.values());
	}

}
