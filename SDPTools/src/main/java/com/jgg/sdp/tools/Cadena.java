/**
 * Implementa funciones de gestion de cadenas 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.tools;

import java.util.Arrays;

public class Cadena {
	
	/**
     * Quita espacios por la izquierda
     *
     * @param s cadena
     * @return una nueva cadena sin espacios por la izquierda
     */
	public static String ltrim(String s) {
	    int i = 0;
	    while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
	        i++;
	    }
	    return s.substring(i);
	}

    /**
     * Quita espacios por la derecha
     *
     * @param s cadena
     * @return una nueva cadena sin espacios por la derecha
     */
	public static String rtrim(String s) {
	    int i = s.length()-1;
	    while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
	        i--;
	    }
	    return s.substring(0,i+1);
	}

	public static String spaces(int length) {
		if (length <= 0) return "";
		char[] data = new char[length];
		Arrays.fill(data, ' ');
		return new String(data);
	}
}
