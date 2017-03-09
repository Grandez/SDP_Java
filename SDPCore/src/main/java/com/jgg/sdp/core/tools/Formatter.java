/*
 * 
 */
package com.jgg.sdp.core.tools;

import java.util.ArrayList;

public class Formatter {

	private final int RIGHT = 72;  // Limite derecho
	private ArrayList<String> lineas = new ArrayList<String>();
	
	/**
     * Format.
     *
     * @param linea
     *            the linea
     * @param margin
     *            the margin
     * @return the array list
     */
	public ArrayList<String> format(String linea, int margin) {
        lineas = new ArrayList<String>();
		String[] tokens = linea.split("\\s+");

		String data = getLeftMargin(margin) + linea;
		String word = getFirstWord(tokens);
		
		// Caso especial de working
		if (word.startsWith("0")) {
			data = ajustaWorking(word, data);
		}
		// Si la linea cabe no hacer nada
		
		if (data.length() < RIGHT) {
			lineas.add(data);
			return lineas;
		}
		

		if (word.compareTo("DATA") == 0  ||  // DATA DIVISION
		    word.startsWith("WORKING")   ||  // WORKING-STORAGE
		    word.startsWith("0")  ) {        // WORKING LEVEL
			lineas.add(data);
			return lineas;
		}
		
		return splitCall(data);
	}
	
	private String getFirstWord(String[] tokens) {
		for (int idx = 0; idx < tokens.length; idx++) {
			if (tokens[idx].length() > 0) return tokens[idx];
		}
		return tokens[0];
	}
	
	private String getLeftMargin(int margin) {
		StringBuffer out = new StringBuffer(margin);
		for (int i = 0; i < margin; i++) out.append(" ");
		return out.toString();
	}

	/**
	 * Indenta correctamente los campos de la working
	 * @param nivel nivel de campo (01, 03, 05, ...)
	 * @param datos la linea actual
	 * @return la linea indentada
	 */
	private String ajustaWorking(String nivel, String datos) {
		int left = Integer.parseInt(nivel);
		int marg = 4;
		
		switch(left) {
		  case 1: marg = 8; break;
		  case 3: marg = 12; break;
		  case 5: marg = 15; break;
		  default: marg = 8;
		}

		StringBuilder buff = new StringBuilder();
		for (int idx = 1 ; idx < marg; idx++) buff.append(" ");
		buff.append(datos);
		return buff.toString();
	}

	private ArrayList<String> splitCall(String linea) {
		ArrayList<String> lineas = new ArrayList<String>();
		String[] tokens = linea.split("\\s+");
		int pos = linea.indexOf(tokens[4]);
		
		lineas.add(linea.substring(0, linea.indexOf(tokens[5])));
		for (int idx = 5; idx < tokens.length; idx++) {
			lineas.add(getLeftMargin(pos) + tokens[idx]);
		}
		return lineas;
		
	}
}
