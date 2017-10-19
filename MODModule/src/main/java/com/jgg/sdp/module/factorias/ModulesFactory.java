/**
 * Factoria de generacion de objetos Module
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.factorias;

import java.util.*;

import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.unit.Source;

public class ModulesFactory {

	private static ArrayList<Module> modules = new ArrayList<Module>();
	private static ArrayList<String> names   = new ArrayList<String>();

	public static Module getCurrentModule() {
		return modules.get(modules.size() - 1);
	}

	public static Module getMainModule() {
		return modules.size() > 0 ? modules.get(0) : null;
	}
	
	public static Module getModule(Source source) {
		Module item = new Module();
		modules.add(item);
		names.add("NONAME");
		return item;
	}

	public static void setModuleName(String name) {
        int idx = 0;
		for (idx = 0; idx < names.size(); idx++) {
			if (names.get(idx).compareTo("NONAME") == 0) break;
		}
		names.set(idx,  name);
	}

	public static Module getDefaultModule() {
         return getModule("NONAME");		
	}
	
	public static Module getModule(String name) {
        int idx = 0;
		for (idx = 0; idx < names.size(); idx++) {
			if (names.get(idx).compareTo(name) == 0) return modules.get(idx);
		}

		Module item = new Module(name);
		modules.add(item);
		names.add(name);
		return item;
	}

	public static List<Module> getModules() {
		return modules;
	}
	
	public static void cleanModules() {
        modules = new ArrayList<Module>();		
        names   = new ArrayList<String>();
	}
	
}
