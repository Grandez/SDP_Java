/**
 * Funciones comunes para la gestion de reglas
 * formatAsxxx
 *     Captura el texto del fichero XML y lo formatea
 *     El objetivo es identificar donde se empezo a escribir y a partir 
 *     de ahi, construir una nueva secuencia como si se hubiera escrito 
 *     desde la columna 1
 *     En el caso de cobol le insertamos el margin
 */
package com.jgg.sdp.rules.processor;

import com.jgg.sdp.tools.Cadena;

public class RulesCommon {

	public static String formatAsString(String data) {
		return formatAsString(data, 0);
	}
	
	public static String[] formatAsArray(String data) {
		return formatAsArray(data, 0);
	}
	
	public static String formatAsString(String data, int margin) {
		String[] l = formatAsArray(data, margin);
		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < l.length; idx++) {
			sb.append(l[idx]);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static String[] formatAsArray(String data, int margin) {
    	String[] lines = data.split("\\\n");
    	String[] lAft = new String[lines.length];
    	int bef = 0;
    	int aft = 0;
    	int indent = 128;
    	
    	for (int idx = 0; idx < lines.length; idx++) {
    		bef = lines[idx].length();
    		lAft[idx] = Cadena.ltrim(lines[idx]); 
    		if (lAft[idx].length() > 0) {
    		    aft = lAft[idx].length();
    		    int diff = bef - aft; 
    		    if (diff < indent) indent = diff;
    		}
    	}
    	String pad = Cadena.spaces(margin);
    	for (int idx = 0; idx < lAft.length; idx++) {
    		lAft[idx] = pad + lAft[idx].substring(indent); 
    	}
        return lAft;    	
    }

}
