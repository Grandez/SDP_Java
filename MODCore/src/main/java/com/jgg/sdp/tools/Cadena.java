/**
 * Implementa funciones de gestion de cadenas 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.tools;

public class Cadena {
	
	public static String trim(String s) {
		return (ltrim(rtrim(s)));
	}
	
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
		String base = "          ";
		StringBuilder tmp = new StringBuilder();
		int pending = length;
		while (pending > base.length()) {
			tmp.append(base);
			pending -= base.length();
		}
		return tmp.toString() + base.substring(0, pending);
	}
	
	public static String left(String s, int len) {
		if (s == null) return null;
		return s.substring(0, len);
	}

	public static String right(String s, int len) {
		if (s == null) return null;
		if (len >= s.length()) return s;
		
		return s.substring(s.length() - len + 1);
	}

	public static String removeQuotes(String s) {
		String n = trim(s);
		if (n.charAt(0) != '\'' && n.charAt(0) != '\"') return n;
		return n.substring(1, n.length() - 1 );
	}
	
	public static String getWord(String s, int n) {
		String ss = s.trim();
		String[] w = ss.split("[ \t]+");
		if (w.length < n) return null;
		return w[n];
	}
}
