/**
 * Factoria de generacion de objetos Module
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.factorias;

import java.util.HashMap;

import com.jgg.sdp.module.base.Module;

public class ModulesFactory {

	private static HashMap<String, Module> modules = new HashMap<String, Module>();
		
	public static Module getModule(String fullName) {
		Module item = modules.get(fullName);
		if (item != null) return item;

		item = new Module(fullName);
		modules.put(fullName, item);
		return item;
	}

}
