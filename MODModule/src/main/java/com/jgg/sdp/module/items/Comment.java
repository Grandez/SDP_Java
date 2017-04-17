/**
 * Implementa un comentario
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

public class Comment {

	/**
     * Indica si es un decorador; es decir, una liena de asteriscos
     * o bordes
     * O por el contrario, es un comentario efectivo
     *
     * @param line La linea de comentario
     * @return true, cierto si es un decorador
     */
	public static boolean isDecorator(String line) {
		String aux = line.substring(0, 72);
		aux = aux.replace('*',' ');
		aux = aux.trim();
		if (aux.length() < 3) return true;
		return false;
	}
}
