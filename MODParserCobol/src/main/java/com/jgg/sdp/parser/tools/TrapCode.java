/**
 * Implementa una linea de codigo de un trap
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.tools;

import java.util.ArrayList;

public class TrapCode implements Comparable<TrapCode> {

	private ArrayList<String> lineas = new ArrayList<String>();
	private int type;
	private int line;
	private int column = 12;
	private int statements;

	public void setType(int type) {
		this.type = type; 
	}
	
	public int getType() {
		return type;
	}
	public void setTrapCode(String linea) {
		lineas.add(linea);
	}
	
	/**
     * Adds the trap code.
     *
     * @param code La linea de codigo
     * @param column La columna donde debe inyectarse
     */
	public void addTrapCode(String code, int column) {
		lineas.add(code);
		this.column = column;
	}

	/**
     * Añade un linea
     *
     * @param index Posicion en la lista de Traps
     * @param line La linea a añadir
     */
	public void addTrapLine(int index, String line) {
		if (index == -1) index = lineas.size();
		lineas.add(index, line);
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setStatements(int statements) {
		this.statements = statements;
	}
	
	public int getStatements() {
		return statements;
	}
	
	public String[] getTrapCode() {
		String[] code = new String[lineas.size()];
		return lineas.toArray(code);
	}
	
	public int compareTo(TrapCode other) {
		if (this.line < other.getLine()) return -1;
		if (this.line > other.getLine()) return 1;
		if (this.statements < other.getStatements()) return -1;
		if (this.statements > other.getStatements()) return  1;
		return 0;
	}
	
}
