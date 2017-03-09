/**
 * Factoria de generacion de objetos Source
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.factorias;

import java.util.HashMap;

import com.jgg.sdp.module.items.Source;

public class SourcesFactory {

	private static HashMap<String, Source> sources = new HashMap<String, Source>();
	
	public static Source getSource(String fullName) {
		Source item = sources.get(fullName);
		if (item != null) return item;

		item = new Source(fullName);
		sources.put(fullName, item);
		return item;
	}
}
